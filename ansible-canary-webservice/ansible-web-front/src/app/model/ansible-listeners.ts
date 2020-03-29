import { Listeners } from './listeners'; 

export class AnsibleListeners {
  hostname: string; 
  listeners: Listeners []; 

  constructor(values: Object = {}){
    Object.assign(this, values);
  }

  public getListenersList(): Listeners[]{
    return this.listeners; 
  }

  public setListenersList(listeners: Listeners[]){
      this.listeners = listeners; 
  }
}
