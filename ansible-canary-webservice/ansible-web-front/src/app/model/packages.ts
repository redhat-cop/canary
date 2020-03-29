export class Packages {

    name: string;
    source: string;
    epoch: number;
    version: string;
    release: string;
    arch: string;

    constructor(values: Object = {}){
        Object.assign(this, values);
    }
}
