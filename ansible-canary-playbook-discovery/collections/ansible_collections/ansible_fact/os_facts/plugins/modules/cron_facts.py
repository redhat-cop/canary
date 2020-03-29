#!/usr/bin/python

# Copyright: (c) 2019, Andrew J. Huffman <ahuffman@redhat.com>
# GNU General Public License v3.0+ (see COPYING or https://www.gnu.org/licenses/gpl-3.0.txt)

ANSIBLE_METADATA = {'metadata_version': '1.1',
                    'status': ['preview'],
                    'supported_by': 'community'}

DOCUMENTATION = '''
---
module: cron_facts
short_description: Collects cron job facts
version_added: "2.8"
description:
    - "Collects cron job facts from a system."
    - "The module can display both parsed and effective cron configurations which is useful when some cron jobs are scripts and others are true schedules."
    - "The display of either effective configurations or parsed configurations can be limited via the module parameters."
options:
    output_strip_comments:
        description:
            - Ignore comment lines from configuration
        default: False
        required: False
    output_parsed_configs:
        description:
            - Parse the configuration files
            - Get #! lines from scripts
            - Get schedules from schedule based files
        default: True
        required: False
    cron_files:
        description:
            - A list of files to scan. If not set the module will search standard cron paths for files.
        default: []
        required: False
author:
    - Andrew J. Huffman (@ahuffman)
    - John Westcott IV (@john-westcott-iv)
'''

EXAMPLES = '''
- name: "Collect all cron data"
  cron_facts:

- name: "Strip comments and empty lines in configuration"
  cron_facts:
    output_strip_comments: True

- name: "Dont parse configuration, only show raw configurations"
  cron_facts:
    output_parsed_configs: False
'''

RETURN = '''
all_scanned_files:
  - configuration: The file scanned as a list
    data:
      schedules:
        - day_of_month: The day of the month of the schedule
          day_of_week: The day of the week of the schedule
          hour: The hour of the schedule
          minute: The minute of the schedule
          month: The month of the schedule
          timeframe: If specified as @yearly/hourly/reboot/monthly
          command: The command to be run
      variables:
        - name: name of the variable
          value: The value of the variable
    path: The file that this information was parsed from
allow:
  path: The path to allow file
  users: List of users in the allow file
deny:
  path: The path to the deny file
  users: List of users in the deny file
files:
  - name: The file name scanned
    user:
    group:
    permissions:
'''

from ansible_collections.ansible_fact.os_facts.plugins.module_utils.fact_gatherer import FactGatherer
from os.path import isfile, isdir, join
from os import walk, stat as os_stat
import re
import stat
from pwd import getpwuid
from grp import getgrgid

class CronGatherer(FactGatherer):
    def get_cron_allow_or_deny(self, allow_or_deny):
        file_name = "/etc/cron.{}".format(allow_or_deny)
        results = None
        # Allow this to raise an exception
        if isfile(file_name):
            results = {
              'path': file_name,
              'users': []
            }
            with open(file_name, 'r') as f:
                for line in f:
                    results['users'].append(user)
        return results

    def get_files(self, path, **kwargs):
        files = []

        if isdir(path):
            # r=root, d=directories, f = files
            for r, d, f in walk(path):
                for a_file in f:
                    if 'extension' not in kwargs or a_file.endswith(kwargs['extension']):
                        files.append(join(r, a_file))

        return files


    def get_cron_files(self):
        cron_paths = []

        if len(self.cron_files) == 0:
            # standard cron locations for cron file discovery
            if isfile("/etc/crontab"):
                cron_paths.append("/etc/crontab")

            cron_dirs = [
                "/etc/cron.hourly",
                "/etc/cron.daily",
                "/etc/cron.weekly",
                "/etc/cron.monthly",
                "/var/spool/cron",
                "/etc/cron.d"
            ]

            # Look for files in cron directories and append to cron_paths
            for a_dir in cron_dirs:
                self.cron_files.extend( get_files(a_dir) )

        else:
            for a_file in self.cron_files:
                if isfile(a_file):
                    cron_paths.append(a_file)

        octal_re = re.compile('^0o{0,1}')

        return_files = []
        for a_file in cron_paths:
            file_stats = os_stat(a_file)
            try:
                user = getpwuid( file_stats.st_uid ).pw_name
            except KeyError as e:
                user = file_stats.st_uid
            try:
                group = getgrgid( file_stats.st_gid ).gr_name
            except KeyError as e:
                group = file_stats.st_gid

            return_files.append({
              'path': a_file,
              'user': user,
              'group': group,
              'permissions': octal_re.sub('', oct( stat.S_IMODE(file_stats.st_mode) )).zfill(4),
            })

        return return_files


    def get_cron_data(self, cron_paths):
        # Output data
        cron_data = list()
        # Regex for parsing data
        variable_re = re.compile(r'^([a-zA-Z0-9_-]*)[ \t]*=[ \t]*(.*)$')
        shebang_re = re.compile(r'^(#!){1}(.*)$')
        schedule_re = re.compile(r'^([0-9\*\-\,\/]+)[\s]+([0-9\*\-\,\/]+)[\s]+([0-9\*\-\,\/]+)[\s]+([0-9a-z\*\-\,\/]+)[\s]+([0-9a-z\*\-\,\/]+)[\s]+([a-z0-9\-\_]*)[\s]*(.*)$', re.IGNORECASE)
        alt_schedule_re = re.compile(r'^(@[a-zA-Z]+)[\s]+([A-Za-z0-9\-\_]*)[\s]*(.*)$')

        # work on each file that was found
        for cron_file in cron_paths:
            job = {
                'path': cron_file['path'],
                'configuration': [],
            }

            with open(cron_file['path'], 'r') as config:
                first_line = True
                for line in config:
                    # Never strip a shebang line
                    if first_line and shebang_re.match(line):
                        job['configuration'].append(line)
                        first_line = False
                    elif self.strip_comments:
                        line = self.remove_comment(line, '#')
                        if line != '':
                            job['configuration'].append(line)
                    else:
                        job['configuration'].append(line)


            if self.parse_configs and len(job['configuration']) > 0:
                job_info = {}

                # Get the shebang line
                shebang_results = shebang_re.search(job['configuration'][0])
                if shebang_results != None:
                    job_info['shell'] = shebang_results.group(2)

                ##  don't try if a shell is set on the file, because it's a script at that point
                if 'shell' not in job_info:
                    for line in job['configuration']:

                        # Capture cron schedules:
                        regular_schedule_results = schedule_re.search(line)
                        if regular_schedule_results != None:
                            if 'schedules' not in job_info:
                                job_info['schedules'] = []
                                job_info['schedules'].append({
                                'minute': regular_schedule_results.group(1),
                                'hour': regular_schedule_results.group(2),
                                'day_of_month': regular_schedule_results.group(3),
                                'month': regular_schedule_results.group(4),
                                'day_of_week': regular_schedule_results.group(5),
                                'command': regular_schedule_results.group(7),
                            })
                            # optional user field in some implementations
                            if regular_schedule_results.group(6):
                                job_info['schedules'][-1]['user'] = regular_schedule_results.group(6)

                        else:
                            # Capture alt cron format
                            alt_schedule_results = alt_schedule_re.search(line)
                            if alt_schedule_results != None:
                                if 'schedules' not in job_info:
                                    job_info['schedules'] = []
                                job_info['schedules'].append({
                                    'timeframe': alt_schedule_results.group(1),
                                    'command': alt_schedule_results.group(3),
                                })
                                if alt_schedule_results.group(2):
                                    job_info['schedules'][-1]['user'] = alt_schedule_results.group(2)

                            else:
                                # Capture script variables
                                variable_results = variable_re.search(line)
                                if variable_results != None:
                                    if 'variables' not in job_info:
                                        job_info['variables'] = []
                                    job_info['variables'].append({
                                        'name': variable_results.group(1),
                                        'value': variable_results.group(2),
                                    })

                job['data'] = job_info

            # append each parsed file
            cron_data.append(job)
        return cron_data

    def doDarwin(self):
        self.doDefault()

    def doDefault(self):

        try:
            cron = { 'files': self.get_cron_files(), }
        except Exception as e:
            self.fail_json(msg="Failed to search for cron files: {}".format(e))

        if len(self.cron_files) == 0:
            # If we are using the default files we can also go after the allow and deny files
            try:
                cron['allow'] = self.get_cron_allow_or_deny('allow')
            except Exception as e:
                self.fail_json(msg="Failed to load cron.allow file: {}".format(e))

            try:
                cron['deny'] = self.get_cron_allow_or_deny('deny')
            except Exception as e:
                self.fail_json(msg="Failed to load cron.deny file: {}".format(e))

        try:
            cron['all_scanned_files'] = self.get_cron_data(cron['files'])
        except Exception as e:
            self.fail_json(msg="Failed to scan cron files: {}".format(e))

        self.exit_json(**{ 'ansible_facts': {'cron': cron } })

    def __init__(self, argument_spec, **kwargs):
        # Call the parent constructor
        super(CronGatherer, self).__init__(argument_spec=argument_spec, **kwargs)
        # Extract the module params into class variables
        self.cron_files = self.params.get('cron_files')
        self.strip_comments = self.params.get('output_strip_comments')
        self.parse_configs = self.params.get('output_parsed_configs')
        # Set additional class variables



def main():
    module = CronGatherer(
        dict(
            cron_files=dict(type='list', default=[], required=False),
            output_strip_comments=dict(type='bool', default=False, required=False),
            output_parsed_configs=dict(type='bool', default=True, required=False),
        ),
        supports_check_mode=True
    )
    module.main()


if __name__ == '__main__':
    main()
