import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssidteMobileResendComponent } from './assidte-mobile-resend.component';

describe('AssidteMobileResendComponent', () => {
  let component: AssidteMobileResendComponent;
  let fixture: ComponentFixture<AssidteMobileResendComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssidteMobileResendComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssidteMobileResendComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
