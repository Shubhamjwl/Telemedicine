import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignScribeComponent } from './assign-scribe.component';

describe('AssignScribeComponent', () => {
  let component: AssignScribeComponent;
  let fixture: ComponentFixture<AssignScribeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AssignScribeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssignScribeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
