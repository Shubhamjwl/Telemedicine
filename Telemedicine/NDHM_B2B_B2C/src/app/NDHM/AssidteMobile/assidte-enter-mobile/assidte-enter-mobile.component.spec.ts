import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssidteEnterMobileComponent } from './assidte-enter-mobile.component';

describe('AssidteEnterMobileComponent', () => {
  let component: AssidteEnterMobileComponent;
  let fixture: ComponentFixture<AssidteEnterMobileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssidteEnterMobileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssidteEnterMobileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
