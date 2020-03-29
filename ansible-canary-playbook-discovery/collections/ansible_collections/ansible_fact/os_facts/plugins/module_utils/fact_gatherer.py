
from ansible.module_utils.basic import AnsibleModule
from ansible.module_utils.facts.system import distribution
from ansible.module_utils.common.process import get_bin_path as fetch_binary
import re

class FactGatherer(AnsibleModule):
    ####### Helper Methods ########

    def remove_comment(self, line, comment_characters):
        return re.sub('[{}].*'.format(comment_characters), '', line)

    def findCommand(self, command):
        try:
            cmd = fetch_binary(command, required=False, opt_dirs=["/opt/freeware/sbin"])
            if cmd is not None:
                return cmd
            else:
                self.warn("Unable to find {} command. Is it installed?".format(command))
                self.exit_json(**dict(msg="Unable to find {} command. Is it installed?".format(command), skipped=True))
        except Exception as e:
            self.warn("Unable to find {} command: {}, is it installed?".format(command, e))
            self.exit_json(**dict(msg="Unable to find {} command: {}, is it installed?".format(command, e), skipped=True))

    ####### End Of Helper Methods ########

    def doDefault(self):
        self.fail_json(msg="No default method was defined for this class")

    def __init__(self, argument_spec, **kwargs):
        # If we have any global arguments, here is where would we put them
        args = dict(
        )
        args.update(argument_spec)

        mutually_exclusive = kwargs.get('mutually_exclusive', [])
        kwargs['mutually_exclusive'] = mutually_exclusive.extend((
        ))

        super(FactGatherer, self).__init__(argument_spec=args, **kwargs)

    def main(self):
        distro_facts = distribution.DistributionFactCollector()
        os_facts = distro_facts.collect(self)
        os_family = os_facts['os_family']
        distro = os_facts['distribution']
        distribution_release = os_facts['distribution_release'].replace('.', '_')
        distribution_major_version = os_facts['distribution_major_version'].replace('.', '_')

        prescidence = [
            "{}{}{}".format(os_family,distro,distribution_release),
            "{}{}{}".format(os_family,distro,distribution_major_version),
            "{}{}".format(os_family,distro),
            "{}".format(os_family),
        ]

        for ordered_item in prescidence:
            method_name = "do{}".format(ordered_item)
            if hasattr(self, method_name):
              self.debug("Selected method {}".format(method_name))
              return getattr(self, method_name)()

        self.warn('No spcific task was found to handle {}/{}/{} attempting to use default gather'.format(os_family, distro, distribution_release))
        return self.doDefault()
