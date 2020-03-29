import { AnsibleMounts } from './ansible-mounts'; 
import { AnsibleLvm } from './ansible-lvm';

export class Facts {
    ansible_distribution_version: string;
    ansible_processor_cores: string;
    ansible_memtotal_mb: string;
    ansible_architecture: string;
    ansible_kernel: string;
    ansible_fqdn: string;
    ansible_mounts: AnsibleMounts[];
    ansible_lvm: AnsibleLvm[];

    constructor(values: Object = {}){
        Object.assign(this, values);
    }

    public getMountsList(): AnsibleMounts[]{
        return this.ansible_mounts; 
    }

    public setMountsList(ansible_mounts: AnsibleMounts[]){
        this.ansible_mounts = ansible_mounts; 
    }

    public getLvmsList(): AnsibleLvm[]{
        return this.ansible_lvm; 
    }

    public setLvmsList(ansible_lvm: AnsibleLvm[]){
        this.ansible_lvm = ansible_lvm; 
    }
}
