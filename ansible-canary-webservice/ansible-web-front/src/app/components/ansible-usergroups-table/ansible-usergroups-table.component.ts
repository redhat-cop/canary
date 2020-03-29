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
import { Users } from '../../model/users'; 
import { Groups } from '../../model/groups'; 
import { FactsService } from '../../service/facts.service'; 


@Component({
  selector: 'app-ansible-usergroups-table',
  templateUrl: './ansible-usergroups-table.component.html',
  styleUrls: ['./ansible-usergroups-table.component.css'] ,
  providers: [FactsService]
})
export class AnsibleUsergroupsTableComponent {

  usersTableColumns = ['username', 'shadow_must_change', 'encrypted_password', 'login_shell', 'home_directory', 'uid', 'gid']; 
  groupsTableColumns = ['gid','password','group_list','group_name']
  hostnames: string[] = [];
  dataSourceUsers = new MatTableDataSource(); 
  dataSourceGroups = new MatTableDataSource(); 
  previousHostname: string;
  control = new FormControl();
  filteredOptions: Observable<string[]>;
  loc: Location;
  loaded: boolean = false;
  loadedUsers: boolean = false;
  loadedGroups: boolean = true;

  @ViewChild(MatPaginator) paginatorUser: MatPaginator;
  @ViewChild(MatPaginator) paginatorGroup: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private factsService: FactsService,private route: ActivatedRoute,
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
      this.onSubmitHostnameStringUsers(host);
      this.onSubmitHostnameStringGroups(host);  
      this.control.setValue(host);
    }
   }

  ngAfterViewInit() {
    this.dataSourceUsers.paginator = this.paginatorUser;
    this.dataSourceGroups.paginator = this.paginatorGroup;
  }

  onSubmitHostname(hostnameEvent){
    let checkChange = hostnameEvent.source.selected; 
    let hostname = hostnameEvent.source.value; 
    if(checkChange){
      if(this.previousHostname!==hostname){
        this.loc.go("usersgroups/"+hostname);
        this.onSubmitHostnameStringUsers(hostname);
        this.onSubmitHostnameStringGroups(hostname);     
      } 
    }
  }


  onSubmitHostnameStringGroups(host: string){
    this.loadedGroups = true;
    this.setLoaded();
    this.previousHostname = host; 
    this.factsService.getGroups(host)
    .subscribe(data => {
      let users = data[0]; 
      if (typeof users === 'undefined'){
        this.dataSourceGroups.data = [];
      }else {
        this.dataSourceGroups.data = users.groups; 
      }
      this.loadedGroups=false;
      this.setLoaded();
    }); 
  }
  onSubmitHostnameStringUsers(host: string){
    this.loadedUsers = true;
    this.setLoaded();
    this.previousHostname = host; 
    this.factsService.getUsers(host)
    .subscribe(data => {
      let users = data[0]; 
      if (typeof users === 'undefined'){
        this.dataSourceUsers.data = [];
      }else {
        this.dataSourceUsers.data = users.users;
      }
      this.loadedUsers=false;
      this.setLoaded();        
    }); 
  }

  setLoaded(){
    this.loaded = this.loadedUsers || this.loadedGroups;
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); 
    filterValue = filterValue.toLowerCase(); 
    this.dataSourceGroups.filter = filterValue;
    this.dataSourceUsers.filter = filterValue;
  }

  filter(val: string): string[] {
    return this.hostnames.filter(option =>
      option.toLowerCase().indexOf(val.toLowerCase()) === 0);
  }
}
