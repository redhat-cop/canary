import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AnsibleFactsTableComponent } from './ansible-facts-table.component';

const routes: Routes = [
  { path: '', component: AnsibleFactsTableComponent }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);