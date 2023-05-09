import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FatchprofileMapComponent } from './fatchprofile-map.component';

describe('FatchprofileMapComponent', () => {
  let component: FatchprofileMapComponent;
  let fixture: ComponentFixture<FatchprofileMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FatchprofileMapComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FatchprofileMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
