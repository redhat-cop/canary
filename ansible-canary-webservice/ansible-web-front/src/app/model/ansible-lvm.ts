export class AnsibleLvm {
    
    name: string;
    size_g: string;
    vg: string;

    constructor(values: Object = {}){
        Object.assign(this, values);
      }
    
}

