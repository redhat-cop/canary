import { Component, ViewChild, AfterViewInit  } from '@angular/core';
import { DataSource } from '@angular/cdk/collections';
import { Observable } from 'rxjs/Observable';
import {FormControl} from '@angular/forms';
import {startWith} from 'rxjs/operators/startWith';
import {map} from 'rxjs/operators/map';  
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import {MatPaginator, MatSort } from '@angular/material';
import { MatTableDataSource } from '@angular/material/table'; 

import { Hostname } from '../../model/hostname';
import { AnsibleListeners } from '../../model/ansible-listeners'; 
import { Listeners } from '../../model/listeners'; 
import { FactsService } from '../../service/facts.service'; 

@Component({
  selector: 'app-ansible-listener-table',
  templateUrl: './ansible-listener-table.component.html',
  styleUrls: ['./ansible-listener-table.component.css'], 
  providers: [FactsService]
})
export class AnsibleListenerTableComponent {

  listenerTableColumns = ['protocol', 'local_address', 'local_port', 'state', 'pid', 'process']; 
  hostnames: string[] = []; 
  dataSource = new MatTableDataSource(); 
  previousHostname: string; 
  control = new FormControl();
  filteredOptions: Observable<string[]>;
  loc: Location;
  loaded: boolean = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private factsService: FactsService, private route: ActivatedRoute,
    private location: Location) { 

    this.factsService.getHostnames()
    .subscribe(hostnames => {
      for(var i=0; i<hostnames.length;i++){
        this.hostnames.push(hostnames[i].hostname);
      }
      
      this.filteredOptions = this.control.valueChanges
      .pipe(
        startWith(''),
        map(host => this.filter(host))
      );
    });
    this.loc=this.location;
    const host = this.route.snapshot.paramMap.get('hostname');
    if(host !== null){
      this.onSubmitHostnameString(host);
      this.control.setValue(host);
    }
  }

  onSubmitHostnameString(host: string){
    this.loaded=true;
    this.previousHostname = host; 
    this.factsService.getListeners(host)
    .subscribe(data => {
      let users = data[0]; 
      if (typeof users === 'undefined'){
        this.dataSource.data = [];
      }else {
        this.dataSource.data = users.listeners; 
      }
      this.loaded=false;
    }); 
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  onSubmitHostname(hostnameEvent){

    let checkChange = hostnameEvent.source.selected; 
    let hostname = hostnameEvent.source.value; 
    
    if (checkChange){
      if(this.previousHostname !== hostname){
        this.loc.go("listeners/"+hostname);
        this.onSubmitHostnameString(hostname);
    }
  }
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); 
    filterValue = filterValue.toLowerCase(); 
    this.dataSource.filter = filterValue;
  }

  filter(val: string): string[] {
    return this.hostnames.filter(option =>
      option.toLowerCase().indexOf(val.toLowerCase()) === 0);
  }
}
