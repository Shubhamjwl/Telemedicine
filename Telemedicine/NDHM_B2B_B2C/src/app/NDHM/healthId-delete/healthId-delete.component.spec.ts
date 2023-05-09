import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthidDeleteComponent } from './healthId-delete.component';

describe('EnterMobileComponent', () => {
  let component: HealthidDeleteComponent;
  let fixture: ComponentFixture<HealthidDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HealthidDeleteComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthidDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
