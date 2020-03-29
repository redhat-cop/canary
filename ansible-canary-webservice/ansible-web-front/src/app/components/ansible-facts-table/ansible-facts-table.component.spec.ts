import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnsibleFactsTableComponent } from './ansible-facts-table.component';

describe('AnsibleFactsTableComponent', () => {
  let component: AnsibleFactsTableComponent;
  let fixture: ComponentFixture<AnsibleFactsTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnsibleFactsTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnsibleFactsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
