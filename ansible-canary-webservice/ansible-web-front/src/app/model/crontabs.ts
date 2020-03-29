export class Crontabs {
    hour: string;
    month: string;
    command: string;
    user: string;
    day: string;
    minute: string;
    weekday: string;

    constructor(values: Object = {}){
        Object.assign(this, values);
    }
}
