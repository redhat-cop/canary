import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AnsibleUsergroupsTableComponent } from './ansible-usergroups-table.component';

const routes: Routes = [
  { path: '', component: AnsibleUsergroupsTableComponent }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);