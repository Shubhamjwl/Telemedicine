import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterScribeComponent } from './register-scribe.component';

describe('RegisterScribeComponent', () => {
  let component: RegisterScribeComponent;
  let fixture: ComponentFixture<RegisterScribeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterScribeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterScribeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
