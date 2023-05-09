import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckerDocCheckListComponent } from './checker-doc-check-list.component';

describe('CheckerDocCheckListComponent', () => {
  let component: CheckerDocCheckListComponent;
  let fixture: ComponentFixture<CheckerDocCheckListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CheckerDocCheckListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckerDocCheckListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
