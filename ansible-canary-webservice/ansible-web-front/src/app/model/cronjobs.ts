import { Crontabs } from './crontabs'; 

export class Cronjobs {

    cronfiles: string[];
    varspool: string[];
    crontab: Crontabs[];

    constructor(values: Object = {}){
        Object.assign(this, values);
    }
}
