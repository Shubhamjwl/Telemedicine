import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BlockUnblockHolidayComponent } from './block-unblock-holiday.component';

describe('BlockUnblockHolidayComponent', () => {
  let component: BlockUnblockHolidayComponent;
  let fixture: ComponentFixture<BlockUnblockHolidayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BlockUnblockHolidayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BlockUnblockHolidayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
