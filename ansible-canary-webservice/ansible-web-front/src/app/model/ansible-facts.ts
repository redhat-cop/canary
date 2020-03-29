import { Facts } from './facts';

export class AnsibleFacts {
  hostname: string; 
  facts: Facts;

  constructor(values: Object = {}){
    Object.assign(this, values);
  }

  public getFacts(): Facts{
    return this.facts; 
  }

  public setFacts(facts: Facts){
      this.facts = facts; 
  } 
}