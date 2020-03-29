import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AnsibleFactsTableComponent } from './components/ansible-facts-table/ansible-facts-table.component';
import { AnsibleServiceTableComponent } from './components/ansible-service-table/ansible-service-table.component';
import { AnsiblePackagesTableComponent } from './components/ansible-packages-table/ansible-packages-table.component';
import { AnsibleUsergroupsTableComponent } from './components/ansible-usergroups-table/ansible-usergroups-table.component';
import { AnsibleProcessesTableComponent } from './components/ansible-processes-table/ansible-processes-table.component';
import { AnsibleListenerTableComponent } from './components/ansible-listener-table/ansible-listener-table.component';
import { AppDataTableComponent } from './components/app-data-table/app-data-table.component'; 

const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  }, 
  { 
    path: 'home', 
    loadChildren: './components/app-data-table/app-data-table.module#AppDataTableModule' 
  },  
  { 
    path: 'packages', 
    loadChildren: './components/ansible-packages-table/ansible-packages-table.module#AnsiblePackagesTableModule' 
  },  
  { 
    path: 'services', 
    loadChildren: './components/ansible-service-table/ansible-service-table.module#AnsibleServiceTableModule' 
  },  
  { 
    path: 'listeners', 
    loadChildren: './components/ansible-listener-table/ansible-listener-table.module#AnsibleListenerTableModule' 
  },  
  { 
    path: 'processes', 
    loadChildren: './components/ansible-processes-table/ansible-processes-table.module#AnsibleProcessesTableModule' 
  },  
  { 
    path: 'facts', 
    loadChildren: './components/ansible-facts-table/ansible-facts-table.module#AnsibleFactsTableModule' 
  },  
  { 
    path: 'usersgroups', 
    loadChildren: './components/ansible-usergroups-table/ansible-usergroups-table.module#AnsibleUsergroupsTableModule' 
  }, 
  { 
    path: 'home/:hostname', 
    loadChildren: './components/app-data-table/app-data-table.module#AppDataTableModule' 
  },  
  { 
    path: 'packages/:hostname', 
    loadChildren: './components/ansible-packages-table/ansible-packages-table.module#AnsiblePackagesTableModule' 
  },  
  { 
    path: 'services/:hostname', 
    loadChildren: './components/ansible-service-table/ansible-service-table.module#AnsibleServiceTableModule' 
  },  
  { 
    path: 'listeners/:hostname', 
    loadChildren: './components/ansible-listener-table/ansible-listener-table.module#AnsibleListenerTableModule' 
  },  
  { 
    path: 'processes/:hostname', 
    loadChildren: './components/ansible-processes-table/ansible-processes-table.module#AnsibleProcessesTableModule' 
  },  
  { 
    path: 'facts/:hostname', 
    loadChildren: './components/ansible-facts-table/ansible-facts-table.module#AnsibleFactsTableModule' 
  },  
  { 
    path: 'usersgroups/:hostname', 
    loadChildren: './components/ansible-usergroups-table/ansible-usergroups-table.module#AnsibleUsergroupsTableModule' 
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: []
})
export class AppRoutingModule {
}