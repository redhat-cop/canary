import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnsibleProcessesTableComponent } from './ansible-processes-table.component';

describe('AnsibleProcessesTableComponent', () => {
  let component: AnsibleProcessesTableComponent;
  let fixture: ComponentFixture<AnsibleProcessesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnsibleProcessesTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnsibleProcessesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
