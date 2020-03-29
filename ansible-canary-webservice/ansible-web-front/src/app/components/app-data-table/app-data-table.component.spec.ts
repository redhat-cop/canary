import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppDataTableComponent } from './app-data-table.component';

describe('AppDataTableComponent', () => {
  let component: AppDataTableComponent;
  let fixture: ComponentFixture<AppDataTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppDataTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppDataTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
