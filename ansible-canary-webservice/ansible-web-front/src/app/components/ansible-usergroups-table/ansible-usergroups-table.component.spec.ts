import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnsibleUsergroupsTableComponent } from './ansible-usergroups-table.component';

describe('AnsibleUsergroupsTableComponent', () => {
  let component: AnsibleUsergroupsTableComponent;
  let fixture: ComponentFixture<AnsibleUsergroupsTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnsibleUsergroupsTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnsibleUsergroupsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
