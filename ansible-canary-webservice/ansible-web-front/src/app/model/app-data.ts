export class AppData {
  id: number;
  appcode: number;
  parent_app_name: string;
  hostname: string;
  fqdn: string;
  sat_ipv4_addr: string;
  services: string;
  environments: string;
  replace_status: boolean; 
  dest_host: string; 

  constructor(values: Object= {}){
    Object.assign(this, values);
  }
}
