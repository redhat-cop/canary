import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import  { FormsModule, ReactiveFormsModule } from '@angular/forms'; 
import 'hammerjs';
import { HttpModule } from '@angular/http';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// import { MaterialModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';


import '../polyfills';
//modules 
import { MatToolbarModule } from '@angular/material';
import { MatSelectModule } from '@angular/material';

//used in Side-panel, will be used when the router is enabled 
import { MatExpansionModule } from '@angular/material';
import { MatSidenavModule } from '@angular/material';
import { MatButtonModule, MatIconModule }from '@angular/material';
//components
import { AppComponent } from './app.component';
// import { TitleComponent } from './components/title/title.component';
import { SidePanelComponent } from './components/side-panel/side-panel.component';
//services
import { AppDataService } from './service/app-data.service';
import { AppDataApiService } from './service/app-data-api.service';
import { FactsService } from './service/facts.service'; 
import { FactsApiService } from './service/facts-api.service';

@NgModule({
  declarations: [
    AppComponent,
    SidePanelComponent,
  ],
  imports: [
    FormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpModule,
    MatToolbarModule,
    MatSelectModule,
    MatExpansionModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    FlexLayoutModule,
    AppRoutingModule,
    ReactiveFormsModule,
   
  ],
  providers: [AppDataService, AppDataApiService, FactsService, FactsApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
