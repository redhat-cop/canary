import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnsibleListenerTableComponent } from './ansible-listener-table.component';

describe('AnsibleListenerTableComponent', () => {
  let component: AnsibleListenerTableComponent;
  let fixture: ComponentFixture<AnsibleListenerTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnsibleListenerTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnsibleListenerTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
