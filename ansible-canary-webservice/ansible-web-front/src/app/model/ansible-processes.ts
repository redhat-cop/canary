import { Processes } from './processes'; 

export class AnsibleProcesses {
    hostname: string; 
    processes: Processes []; 
    
    constructor(values: Object = {}){
        Object.assign(this, values);
    }
    
    public getProcessesList(): Processes[]{
        return this.processes; 
    }
    public setProcessesList(processes: Processes[]){
        this.processes = processes; 
    }
}
