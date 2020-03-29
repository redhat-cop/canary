
export class Listeners {

    protocol: string;
    local_address: string;
    local_port: string;
    state: string;
    pid: string;
    process: string;

    constructor(values: Object = {}){
        Object.assign(this, values);
    }

}


