import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AnsiblePackagesTableComponent } from './ansible-packages-table.component';

const routes: Routes = [
  { path: '', component: AnsiblePackagesTableComponent }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);