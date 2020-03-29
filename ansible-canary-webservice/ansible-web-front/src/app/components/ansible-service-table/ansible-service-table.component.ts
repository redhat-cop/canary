import { Component, ViewChild, AfterViewInit } from '@angular/core';
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
import { AnsibleServices } from '../../model/ansible-services';
import { Services } from '../../model/services';
import { FactsService } from '../../service/facts.service'; 

@Component({
  selector: 'app-ansible-service-table',
  templateUrl: './ansible-service-table.component.html',
  styleUrls: ['./ansible-service-table.component.css'],
  providers: [FactsService]  
})
export class AnsibleServiceTableComponent {

  serviceTableColumns = ['service_name', 'state', 'source']; 
  hostnames: string[];
  dataSource = new MatTableDataSource(); 
  previousHostname: string;
  control = new FormControl();
  filteredOptions: Observable<string[]>; 
  loc: Location;
  loaded: boolean = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private factsService: FactsService,private route: ActivatedRoute,
    private location: Location) { 
    this.hostnames = [];
    this.factsService.getHostnames()
    .subscribe(hostname => {
      for(var i=0; i<hostname.length;i++){
        this.hostnames.push(hostname[i].hostname);
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
    this.factsService.getServices(host)
    .subscribe(data => {
      let users = data[0]; 
      if (typeof users === 'undefined'){
        this.dataSource.data = [];
      }else {
        this.dataSource.data = users.services; 
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

    if(checkChange){
      if(this.previousHostname !== hostname){
        this.loc.go("services/"+hostname);
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
