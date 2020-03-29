// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.

export const environment = {
  production: false,
  contextRoot: 'http://localhost:3000',

  appCode_url: '/appcodes/', 
  appDataById_url: '/appdata/?appcode=',

  hostname_url: '/servers/',
  listener_url: '/ansible_listeners/?hostname=',
  service_url: '/ansible_services/?hostname=', 
  packages_url: '/ansible_packages/?hostname=', 
  processes_url: '/ansible_processes/?hostname=',
  users_url: '/ansible_users/?hostname=', 
  groups_url: '/ansible_groups/?hostname=', 
  cronjobs_url: '/ansible_cronjobs/?hostname=',
  facts_url: '/ansible_facts/?hostname='
};
