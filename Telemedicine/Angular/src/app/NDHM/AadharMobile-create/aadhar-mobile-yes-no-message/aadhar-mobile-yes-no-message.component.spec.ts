import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AadharMobileYesNoMessageComponent } from './aadhar-mobile-yes-no-message.component';

describe('AadharMobileYesNoMessageComponent', () => {
  let component: AadharMobileYesNoMessageComponent;
  let fixture: ComponentFixture<AadharMobileYesNoMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AadharMobileYesNoMessageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AadharMobileYesNoMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
