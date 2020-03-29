import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';

import { Hostname } from '../model/hostname'; 
import { AnsibleServices } from '../model/ansible-services';
import { AnsibleListeners } from '../model/ansible-listeners'; 
import { AnsiblePackages } from '../model/ansible-packages'; 
import { AnsibleProcesses} from '../model/ansible-processes';
import { Cronjobs } from '../model/cronjobs'; 
import { Listeners } from '../model/listeners'; 
import { Processes } from '../model/processes'; 
import { Packages } from '../model/packages'; 
import { Services } from '../model/services';
import { AnsibleCronjobs } from '../model/ansible-cronjobs';
import { AnsibleUsers } from '../model/ansible-users'; 
import { AnsibleGroups } from '../model/ansible-groups'; 
import { AnsibleLvm } from '../model/ansible-lvm';
import { Facts } from '../model/facts'; 
import { AnsibleFacts } from '../model/ansible-facts'; 

const CONTEXT_ROOT = environment.contextRoot;
const HOSTNAME_URL = environment.hostname_url;
const SERVICE_URL = environment.service_url;
const LISTENER_URL = environment.listener_url;  
const PACKAGES_URL = environment.packages_url;  
const PROCESSES_URL = environment.processes_url;
const CRONJOBS_URL = environment.cronjobs_url;
const USERS_URL = environment.users_url; 
const GROUPS_URL = environment.groups_url;
const FACTS_URL = environment.facts_url;
@Injectable()
export class FactsApiService {

  constructor( private http: Http) { }

  public getHostnames(): Observable<Hostname[]> {
    return this.http
    .get(CONTEXT_ROOT + HOSTNAME_URL) 
    .map( response => {
      const appCodes = response.json(); 
      return appCodes.map((row) => new Hostname(row)); 
    })
    .catch(this.handleError); 
    
  }

  public getServices(hostname: string):Observable<AnsibleServices> {
    return this.http
    .get(CONTEXT_ROOT + SERVICE_URL + hostname)
    .map(response => {
     const services = response.json();
      let data = services.map((row) => {
        let ansibleServices = new AnsibleServices(row); 
        if(ansibleServices.getServicesList() == null){
          ansibleServices.setServicesList([]); 
        }
        return ansibleServices; 
      }); 
      return data; 
    })
    .catch(this.handleError);
  }

  public getPackages(hostname: string):Observable<AnsiblePackages> {
    return this.http
    .get(CONTEXT_ROOT + PACKAGES_URL + hostname)
    .map(response => {
     const packages = response.json();
      let data = packages.map((row) => {
        let ansiblePackages = new AnsiblePackages(row); 
        if(ansiblePackages.getPackagesList() == null){
          ansiblePackages.setPackagesList([]); 
        }
        return ansiblePackages; 
      }); 
     return data;  
    })
    .catch(this.handleError); 
  }

  public getListeners(hostname: string):Observable<AnsibleListeners> {
    return this.http
    .get(CONTEXT_ROOT + LISTENER_URL + hostname)
    .map(response => {
     const listeners = response.json();
      let data = listeners.map((row) => {
        let ansibleListeners = new AnsibleListeners(row); 
        if(ansibleListeners.getListenersList() == null){
          ansibleListeners.setListenersList([]); 
        }
        return ansibleListeners; 
      }); 
      return data; 
    })
    .catch(this.handleError); 
  }

  public getCronjobs(hostname: string):Observable<AnsibleCronjobs> {
    return this.http
    .get(CONTEXT_ROOT + CRONJOBS_URL + hostname)
    .map(response => {
     const cronjobs = response.json();
      let data = cronjobs.map((row) => {
        let ansibleCronjobs = new AnsibleCronjobs(row); 
        if(ansibleCronjobs.getCronJobList() == null){
          ansibleCronjobs.setCronJobList([]); 
        }
        return ansibleCronjobs; 
      }); 
      return data; 
    })
    .catch(this.handleError); 
  }

  public getUsers(hostname: string): Observable<AnsibleUsers> {
    return this.http
    .get(CONTEXT_ROOT + USERS_URL + hostname)
    .map(response => {
      const users = response.json(); 
      let data = users.map((row) => {
        let ansibleUsers = new AnsibleUsers(row); 
        if(ansibleUsers.getUsersList() == null){
          ansibleUsers.setUsersList([]); 
        }
        return ansibleUsers; 
      })
      return data; 
    }).catch(this.handleError); 
  }

  public getGroups(hostname: string): Observable<AnsibleGroups> {
    return this.http
    .get(CONTEXT_ROOT + GROUPS_URL + hostname)
    .map(response => {
      const users = response.json(); 
      let data = users.map((row) => {
        let ansibleGroups = new AnsibleGroups(row); 
        if(ansibleGroups.getGroupsList() == null){
          ansibleGroups.setGroupsList([]); 
        }
        return ansibleGroups; 
      })
      return data; 
    }).catch(this.handleError); 
  }

  public getProcesses(hostname: string):Observable<AnsibleProcesses> {
    return this.http
    .get(CONTEXT_ROOT + PROCESSES_URL + hostname)
    .map(response => {
     const processes = response.json();
      let data = processes.map((row) =>{
        let ansibleProcesses = new AnsibleProcesses(row); 
        if(ansibleProcesses.getProcessesList() == null){
          ansibleProcesses.setProcessesList([]); 
        }
        return ansibleProcesses; 
      }); 
      return data; 
    })
    .catch(this.handleError);
  }

  public getFacts(hostname: string) {
    return this.http
    .get(CONTEXT_ROOT + FACTS_URL + hostname)
    .map(response => {
     const facts = response.json();
      let data = facts.map((row) => {
        let ansibleFacts = new AnsibleFacts(row); 
        if(ansibleFacts.getFacts() == null){
          ansibleFacts.setFacts(null); 
        }
        return ansibleFacts; 
      }); 
      return data; 
    })
    .catch(this.handleError); 
  }

  private handleError (error: Response | any) {
    console.error('FactsApiService::handleError', error);
    return Observable.throw(error);
  }
}
