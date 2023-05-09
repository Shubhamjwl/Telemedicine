import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthidSearchComponent } from './healthid-search.component';

describe('HealthidSearchComponent', () => {
  let component: HealthidSearchComponent;
  let fixture: ComponentFixture<HealthidSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HealthidSearchComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthidSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
