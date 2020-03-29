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
  import { AnsibleCronjobs } from '../../model/ansible-cronjobs'; 
  import { Crontabs } from '../../model/crontabs'; 
  import { Cronjobs } from '../../model/cronjobs'; 
  import { Facts } from '../../model/facts'; 
  import { FactsService } from '../../service/facts.service'; 
import { forEach } from '@angular/router/src/utils/collection';
@Component({
  selector: 'app-ansible-facts-table',
  templateUrl: './ansible-facts-table.component.html',
  styleUrls: ['./ansible-facts-table.component.css'],
  providers: [FactsService]
})
export class AnsibleFactsTableComponent {
  
    crontabsTableColumns = ['hour', 'month', 'command', 'user', 'day', 'minute', 'weekday']; 
    factsTableColumns = ['ansible_distribution_version', 'ansible_processor_cores', 'ansible_memtotal_mb', 'ansible_architecture', 'ansible_kernel', 'ansible_fqdn'];
    mountsTableColumns = ['uuid', 'size_total', 'mount', 'size_available', 'fstype', 'device', 'options']; 
    lvmsTableColumns = ['name', 'size_g', 'vg'];  
    hostnames: string[] = []; 
    dataSourceCrontabs = new MatTableDataSource(); 
    dataSourceFacts = new MatTableDataSource(); 
    dataSourceMounts = new MatTableDataSource(); 
    dataSourceLvms = new MatTableDataSource(); 
    selectedHostname: string; 
    control = new FormControl();
    filteredOptions: Observable<string[]>;
    previousHostname: string; 
    loc: Location;
    loaded: boolean = false;
    loadedCrontabs: boolean = false;
    loadedFacts: boolean = false;
  
    @ViewChild(MatPaginator) paginator: MatPaginator;
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
          //Add code for other options
      });
      this.loc=this.location;
      const host = this.route.snapshot.paramMap.get('hostname');
      if(host !== null){
        this.onSubmitHostnameCrontabsString(host);
        this.onSubmitHostnameFactsString(host);
        this.control.setValue(host);
      }
     }
    
    onSubmitHostnameCrontabsString(host: string){
      this.loadedCrontabs=true; 
      this.setLoaded();     
      this.previousHostname=host;
      this.factsService.getCronjobs(host)
      .subscribe( 
          data => {
          let cron = data[0]; 
          if (typeof cron === 'undefined'){
            this.dataSourceCrontabs.data = []; 
          }else{
            this.dataSourceCrontabs.data = cron.cronjobs.crontabs;      
          }
          this.loadedCrontabs=false;  
          this.setLoaded();         
        });
    }

    onSubmitHostnameFactsString(host: string){
      this.loadedFacts=true; 
      this.setLoaded();     
      this.previousHostname=host;
      this.factsService.getFacts(host)
      .subscribe( 
          data => {
          let fact = data[0]; 
          if (typeof fact === 'undefined'){
            this.dataSourceFacts.data = [];
            this.dataSourceMounts.data = [];
            this.dataSourceLvms.data = []; 
          }else{
            this.dataSourceFacts.data = [fact.facts];   
            this.dataSourceMounts.data = fact.facts.ansible_mounts;   
            this.dataSourceLvms.data = fact.facts.ansible_lvm;      
          }
          this.loadedFacts=false; 
          this.setLoaded();     
        });
    }
  
    ngAfterViewInit() {
      this.dataSourceCrontabs.paginator = this.paginator;
    }
  
    onSubmitHostname(hostnameEvent){
      let checkChange = hostnameEvent.source.selected; 
      let hostname = hostnameEvent.source.value; 
      if(checkChange){
        if(this.previousHostname !== hostname){
          this.loc.go("facts/"+hostname);
          this.onSubmitHostnameCrontabsString(hostname);
          this.onSubmitHostnameFactsString(hostname);
        }
      }   
    }
  
    
  
    applyFilter(filterValue: string) {
      filterValue = filterValue.trim(); 
      filterValue = filterValue.toLowerCase(); 
      this.dataSourceCrontabs.filter = filterValue;
      this.dataSourceFacts.filter = filterValue;
      this.dataSourceMounts.filter = filterValue;
      this.dataSourceLvms.filter = filterValue;
    }
    
        
    filter(val: string): string[] {
      return this.hostnames.filter(option =>
        option.toLowerCase().indexOf(val.toLowerCase()) === 0);
    }

    setLoaded(){
      this.loaded = this.loadedCrontabs || this.loadedFacts;
    }

  //REPEAT ALL STEPS ABOVE FOR CRONTAB FOR ANY OTHER TABLE

}
