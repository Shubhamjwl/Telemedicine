import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookAppointmnetUrlComponent } from './book-appointmnet-url.component';

describe('BookAppointmnetUrlComponent', () => {
  let component: BookAppointmnetUrlComponent;
  let fixture: ComponentFixture<BookAppointmnetUrlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookAppointmnetUrlComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BookAppointmnetUrlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
