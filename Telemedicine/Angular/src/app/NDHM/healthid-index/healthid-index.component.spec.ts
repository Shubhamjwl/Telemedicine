import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthidIndexComponent } from './healthid-index.component';

describe('HealthidIndexComponent', () => {
  let component: HealthidIndexComponent;
  let fixture: ComponentFixture<HealthidIndexComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HealthidIndexComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthidIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
