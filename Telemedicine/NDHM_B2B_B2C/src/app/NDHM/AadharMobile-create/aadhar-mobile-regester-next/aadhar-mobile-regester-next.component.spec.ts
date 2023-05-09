import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AadharMobileRegesterNextComponent } from './aadhar-mobile-regester-next.component';

describe('AadharMobileRegesterNextComponent', () => {
  let component: AadharMobileRegesterNextComponent;
  let fixture: ComponentFixture<AadharMobileRegesterNextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AadharMobileRegesterNextComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AadharMobileRegesterNextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
