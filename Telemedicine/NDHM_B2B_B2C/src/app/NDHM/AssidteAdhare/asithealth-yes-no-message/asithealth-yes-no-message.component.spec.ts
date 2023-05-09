import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsithealthYesNoMessageComponent } from './asithealth-yes-no-message.component';

describe('AsithealthYesNoMessageComponent', () => {
  let component: AsithealthYesNoMessageComponent;
  let fixture: ComponentFixture<AsithealthYesNoMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AsithealthYesNoMessageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AsithealthYesNoMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
