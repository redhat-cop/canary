import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AnsibleServiceTableComponent } from './ansible-service-table.component';

const routes: Routes = [
  { path: '', component: AnsibleServiceTableComponent }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);