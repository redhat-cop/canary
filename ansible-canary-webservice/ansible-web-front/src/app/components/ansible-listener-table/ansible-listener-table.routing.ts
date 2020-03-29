import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AnsibleListenerTableComponent } from './ansible-listener-table.component';

const routes: Routes = [
  { path: '', component: AnsibleListenerTableComponent }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);