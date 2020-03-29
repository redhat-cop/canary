import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnsiblePackagesTableComponent } from './ansible-packages-table.component';

describe('AnsiblePackagesTableComponent', () => {
  let component: AnsiblePackagesTableComponent;
  let fixture: ComponentFixture<AnsiblePackagesTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnsiblePackagesTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnsiblePackagesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
