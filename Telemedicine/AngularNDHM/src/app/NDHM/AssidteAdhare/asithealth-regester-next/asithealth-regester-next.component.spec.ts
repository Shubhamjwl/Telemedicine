import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsithealthRegesterNextComponent } from './asithealth-regester-next.component';

describe('AsithealthRegesterNextComponent', () => {
  let component: AsithealthRegesterNextComponent;
  let fixture: ComponentFixture<AsithealthRegesterNextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AsithealthRegesterNextComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AsithealthRegesterNextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
