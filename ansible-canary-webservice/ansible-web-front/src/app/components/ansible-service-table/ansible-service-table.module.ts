import { NgModule } from '@angular/core';
import { MatCardModule, MatTooltip } from '@angular/material';
import { MatTableModule } from '@angular/material';
import { MatPaginatorModule } from '@angular/material';
import { MatFormFieldModule, MatAutocompleteModule } from '@angular/material';
import { MatSelectModule } from '@angular/material';
import { MatInputModule } from '@angular/material';
import { MatTabsModule } from '@angular/material';
import { MatProgressSpinnerModule } from '@angular/material';
import { MatTooltipModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';


import { AnsibleServiceTableComponent }   from './ansible-service-table.component';
import { routing } from './ansible-service-table.routing';

@NgModule({
  imports: [routing,
      MatProgressSpinnerModule,
      MatTooltipModule,
      CommonModule,
      FormsModule,
      MatCardModule,
      MatTableModule,
      MatFormFieldModule,
      MatInputModule,
      MatSelectModule,
      ReactiveFormsModule,
      MatAutocompleteModule,
      MatPaginatorModule
    ],
  declarations: [AnsibleServiceTableComponent]
})
export class AnsibleServiceTableModule {}

