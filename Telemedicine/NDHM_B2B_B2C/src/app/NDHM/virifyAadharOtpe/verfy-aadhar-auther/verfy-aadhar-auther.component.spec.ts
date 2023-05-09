import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerfyAadharAutherComponent } from './verfy-aadhar-auther.component';

describe('VerfyAadharAutherComponent', () => {
  let component: VerfyAadharAutherComponent;
  let fixture: ComponentFixture<VerfyAadharAutherComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VerfyAadharAutherComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VerfyAadharAutherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
