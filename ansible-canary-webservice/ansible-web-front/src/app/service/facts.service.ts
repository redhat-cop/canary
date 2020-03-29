import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';  
import { Hostname } from '../model/hostname'; 
import { AnsibleListeners } from '../model/ansible-listeners';
import { AnsiblePackages } from '../model/ansible-packages'; 
import { Packages } from '../model/packages';
import { AnsibleProcesses } from '../model/ansible-processes'; 
import { Services } from '../model/services';
import { Processes } from '../model/processes';
import { Listeners } from '../model/listeners'; 
import { FactsApiService } from '../service/facts-api.service'; 
import { AnsibleServices } from '../model/ansible-services';
import { AnsibleCronjobs } from '../model/ansible-cronjobs'; 
import { Cronjobs } from '../model/cronjobs';
import { AnsibleUsers } from '../model/ansible-users'; 
import { AnsibleGroups } from '../model/ansible-groups';
import { AnsibleFacts } from '../model/ansible-facts';
import { AnsibleMounts } from '../model/ansible-mounts'; 
import { AnsibleLvm } from '../model/ansible-lvm'; 
import { Users } from '../model/users';
import { Groups } from '../model/groups';


@Injectable()
export class FactsService {

  constructor(private api: FactsApiService) { }

  getHostnames(): Observable<Hostname[]> {
      return this.api.getHostnames(); 
  }
  
  getServices(hostname: string): Observable<AnsibleServices> {
    return this.api.getServices(hostname); 
  }

  getListeners(hostname: string): Observable<AnsibleListeners> {
    return this.api.getListeners(hostname); 
  }

  getPackages(hostname: string): Observable<AnsiblePackages> {
    return this.api.getPackages(hostname); 
  }

  getProcesses(hostname: string): Observable<AnsibleProcesses> {
    return this.api.getProcesses(hostname); 
  }

  getCronjobs(hostname: string): Observable<AnsibleCronjobs> {
    return this.api.getCronjobs(hostname); 
  }

  getFacts(hostname: string): Observable<AnsibleFacts> {
    return this.api.getFacts(hostname); 
  }

  getUsers(hostname: string): Observable<AnsibleUsers> {
    return this.api.getUsers(hostname); 
  }
  getGroups(hostname: string): Observable<AnsibleGroups>{
    return this.api.getGroups(hostname);
  }
  
  private handleError (error: Response | any) {
    console.error('FactsApiService::handleError', error);
    return Observable.throw(error);
  }
}
