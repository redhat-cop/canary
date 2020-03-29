import { Services } from './services';

export class AnsibleServices {
    hostname: string; 
    services: Services []; 
  
    constructor(values: Object = {}){
      Object.assign(this, values);
    }
  
    public getServicesList(): Services[]{
      return this.services; 
    }

    public setServicesList(services: Services[]){
      this.services = services; 
    }
}