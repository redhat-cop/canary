import { ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AppDataTableComponent } from './app-data-table.component';

const routes: Routes = [
  { path: '', component: AppDataTableComponent }
];

export const routing: ModuleWithProviders = RouterModule.forChild(routes);