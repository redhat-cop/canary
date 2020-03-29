import { Packages } from './packages'; 

export class AnsiblePackages {
    hostname: string; 
    packages: Packages []; 
    
    constructor(values: Object = {}){
        Object.assign(this, values);
    }
    
    public getPackagesList(): Packages[]{
        return this.packages; 
    }

    public setPackagesList(packages: Packages[]){
        this.packages = packages; 
    }
}
