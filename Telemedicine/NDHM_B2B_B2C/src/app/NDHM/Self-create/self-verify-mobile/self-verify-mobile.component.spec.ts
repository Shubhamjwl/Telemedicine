import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelfVerifyMobileComponent } from './self-verify-mobile.component';

describe('SelfVerifyMobileComponent', () => {
  let component: SelfVerifyMobileComponent;
  let fixture: ComponentFixture<SelfVerifyMobileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SelfVerifyMobileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SelfVerifyMobileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
