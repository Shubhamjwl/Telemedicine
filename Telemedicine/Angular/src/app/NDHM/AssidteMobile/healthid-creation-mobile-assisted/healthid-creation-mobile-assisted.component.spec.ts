import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthidCreationMobileAssistedComponent } from './healthid-creation-mobile-assisted.component';

describe('HealthidCreationMobileAssistedComponent', () => {
  let component: HealthidCreationMobileAssistedComponent;
  let fixture: ComponentFixture<HealthidCreationMobileAssistedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HealthidCreationMobileAssistedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthidCreationMobileAssistedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
