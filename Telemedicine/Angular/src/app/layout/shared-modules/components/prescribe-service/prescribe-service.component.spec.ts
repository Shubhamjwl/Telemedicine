import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrescribeServiceComponent } from './prescribe-service.component';

describe('PrescribeServiceComponent', () => {
  let component: PrescribeServiceComponent;
  let fixture: ComponentFixture<PrescribeServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrescribeServiceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrescribeServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
