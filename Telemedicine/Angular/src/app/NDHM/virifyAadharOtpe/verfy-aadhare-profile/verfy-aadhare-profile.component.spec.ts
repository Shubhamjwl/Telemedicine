import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerfyAadhareProfileComponent } from './verfy-aadhare-profile.component';

describe('VerfyAadhareProfileComponent', () => {
  let component: VerfyAadhareProfileComponent;
  let fixture: ComponentFixture<VerfyAadhareProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VerfyAadhareProfileComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VerfyAadhareProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
