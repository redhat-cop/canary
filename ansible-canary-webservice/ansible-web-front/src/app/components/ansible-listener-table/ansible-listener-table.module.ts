import { NgModule } from '@angular/core';
import { MatCardModule, MatProgressSpinner } from '@angular/material';
import { MatTableModule } from '@angular/material';
import { MatPaginatorModule } from '@angular/material';
import { MatFormFieldModule, MatAutocompleteModule } from '@angular/material';
import { MatSelectModule } from '@angular/material';
import { MatInputModule } from '@angular/material';
import { MatTabsModule } from '@angular/material';
import { MatTooltipModule } from '@angular/material';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MatProgressSpinnerModule } from '@angular/material';


import { AnsibleListenerTableComponent }   from './ansible-listener-table.component';
import { routing } from './ansible-listener-table.routing';

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
  declarations: [AnsibleListenerTableComponent]
})
export class AnsibleListenerTableModule {}
