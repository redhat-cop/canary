export class Groups {
    gid: string;
    password: string;
    group_list: string;
    group_name: string;

    constructor(values: Object = {}){
        Object.assign(this, values);
    }
    
}
