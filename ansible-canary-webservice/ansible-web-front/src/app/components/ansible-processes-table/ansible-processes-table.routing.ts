import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AnsibleProcessesTableComponent } from './ansible-processes-table.component';

const routes: Routes = [
  { path: '', component: AnsibleProcessesTableComponent }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);