import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigDoctorListComponent } from './config-doctor-list.component';

describe('ConfigDoctorListComponent', () => {
  let component: ConfigDoctorListComponent;
  let fixture: ComponentFixture<ConfigDoctorListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfigDoctorListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigDoctorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
