import { Users } from './users';

export class AnsibleUsers {

    hostname: string; 
    users: Users []; 

    constructor(values: Object = {}){
        Object.assign(this, values);
    }

    public getUsersList(): Users[]{
        return this.users; 
    }

    public setUsersList(users: Users[]){
        this.users = users; 
    }
    
}
