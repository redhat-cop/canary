import { NgModule } from '@angular/core';
import { MatCardModule } from '@angular/material';
import { MatTableModule } from '@angular/material';
import { MatPaginatorModule } from '@angular/material';
import { MatProgressSpinnerModule } from '@angular/material';
import { MatFormFieldModule, MatAutocompleteModule } from '@angular/material';
import { MatSelectModule } from '@angular/material';
import { MatInputModule } from '@angular/material';
import { MatTabsModule } from '@angular/material';
import { MatTooltipModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';


import { AnsibleProcessesTableComponent }   from './ansible-processes-table.component';
import { routing } from './ansible-processes-table.routing';

@NgModule({
  imports: [routing,
      CommonModule,
      MatTooltipModule,
      MatProgressSpinnerModule,
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
  declarations: [AnsibleProcessesTableComponent]
})
export class AnsibleProcessesTableModule {}
