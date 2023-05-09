import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsithealthEnterMobileComponent } from './asithealth-enter-mobile.component';

describe('AsithealthEnterMobileComponent', () => {
  let component: AsithealthEnterMobileComponent;
  let fixture: ComponentFixture<AsithealthEnterMobileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AsithealthEnterMobileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AsithealthEnterMobileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
