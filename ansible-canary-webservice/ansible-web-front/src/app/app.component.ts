import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
import {MatSidenav} from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'], 
})

export class AppComponent {
  title = 'app';
  events= [];
}

