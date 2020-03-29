import { Groups } from './groups';

export class AnsibleGroups {

    hostname: string; 
    groups: Groups[];

    constructor(values: Object = {}){
        Object.assign(this, values);
    }

    public getGroupsList(): Groups[]{
        return this.groups; 
    }

    public setGroupsList(groups: Groups[]){
        this.groups = groups; 
    }
}
