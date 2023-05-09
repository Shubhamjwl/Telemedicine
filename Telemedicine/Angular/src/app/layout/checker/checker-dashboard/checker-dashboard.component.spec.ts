import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckerDashboardComponent } from './checker-dashboard.component';

describe('CheckerDashboardComponent', () => {
  let component: CheckerDashboardComponent;
  let fixture: ComponentFixture<CheckerDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CheckerDashboardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckerDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
