export class Processes {
    
    TTY: string;
    CPU_Perc: string;
    PID: string;
    MEM_Perc: string;
    COMMAND: string;
    USER: string;
    STAT: string;

    constructor(values: Object = {}){
        Object.assign(this, values);
    }
}
