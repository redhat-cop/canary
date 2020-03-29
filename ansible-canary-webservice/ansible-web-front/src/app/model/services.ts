export class Services {
    name: string;
    state: string;
    source: string;
    
    constructor(values: Object = {}){
        Object.assign(this, values);
    }
}
