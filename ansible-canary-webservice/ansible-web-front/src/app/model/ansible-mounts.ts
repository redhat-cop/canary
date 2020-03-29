export class AnsibleMounts {

    uuid: string;
    size_total: string;
    mount: string;
    size_available: string;
    fstype: string;
    device: string;
    options: string;

    constructor(values: Object = {}){
        Object.assign(this, values);
      }

}
