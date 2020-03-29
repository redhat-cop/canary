import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnsibleServiceTableComponent } from './ansible-service-table.component';

describe('AnsibleServiceTableComponent', () => {
  let component: AnsibleServiceTableComponent;
  let fixture: ComponentFixture<AnsibleServiceTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnsibleServiceTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnsibleServiceTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
