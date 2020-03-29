import { Cronjobs } from './cronjobs'; 

export class AnsibleCronjobs {
    hostname: string; 
    cronjobs: Cronjobs []; 
  
    constructor(values: Object = {}){
      Object.assign(this, values);
    }
  
    public getCronJobList(): Cronjobs[]{
      return this.cronjobs; 
    }

    public setCronJobList(cronjobs: Cronjobs[]){
      this.cronjobs = cronjobs; 
    }
}
