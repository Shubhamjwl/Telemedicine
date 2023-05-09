import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CongratulationsModalComponent } from './congratulations-modal.component';

describe('CongratulationsModalComponent', () => {
  let component: CongratulationsModalComponent;
  let fixture: ComponentFixture<CongratulationsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CongratulationsModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CongratulationsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
