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

import { AppCode } from '../../model/app-code'; 
import { Hostname } from '../../model/hostname';
import { AppData }  from '../../model/app-data'; 
import { AppDataService } from '../../service/app-data.service'; 
import { print } from 'util';

@Component({
  selector: 'app-app-data-table',
  templateUrl: './app-data-table.component.html',
  styleUrls: ['./app-data-table.component.css'], 
  providers: [AppDataService]
})

export class AppDataTableComponent  {

  appTableColumns = ["app", "eai", "hostname", "ip", "fqdn", "migration_status", "dest_host"];
  appcodes: string[] = [];
  previousAppCode: string;  
  dataSource = new MatTableDataSource(); 
  control = new FormControl();
  filteredOptions: Observable<string[]>;
  loc: Location;
  loaded: boolean = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private appDataService: AppDataService,private route: ActivatedRoute,
    private location: Location) { 
    

    this.appDataService.getAppCodes()
    .subscribe(appcodes => {
      for(var i=0; i<appcodes.length;i++){
        this.appcodes.push(appcodes[i].appcode.toString());
      }
      this.filteredOptions = this.control.valueChanges
      .pipe(
        startWith(''),
        map(ac => this.filterCode(ac))
      );
    });
    this.loc=this.location;
    const app = +this.route.snapshot.paramMap.get('appcode');
    if(app !== 0){
      this.onSubmitAppCodeNumber(app);
      this.control.setValue(app);
    }
  }

  onSubmitAppCodeNumber(appcode: number){
    this.loaded=true;
    this.previousAppCode = appcode.toString();
    this.appDataService.getAppDataByCode(appcode)
    .subscribe(data => {
      this.dataSource.data = data; 
      this.loaded=false;
    });     
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  onSubmitAppCode(appcodeEvent){
    let appcode = appcodeEvent.source.value; 
    let checkChange = appcodeEvent.source.selected; 
    
    if(checkChange){
      if(this.previousAppCode !== appcode){
        this.loc.go("home/"+appcode);
        this.onSubmitAppCodeNumber(parseInt(appcode,10));
      }
    }
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); 
    filterValue = filterValue.toLowerCase(); 
    this.dataSource.filter = filterValue;
  }

  filterCode(app: string): string[] {
    return this.appcodes.filter(option =>
      option.indexOf(app.toLowerCase()) === 0);
  }
}