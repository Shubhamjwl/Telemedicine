import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TmResourceComponent } from './tm-resource.component';

describe('TmResourceComponent', () => {
  let component: TmResourceComponent;
  let fixture: ComponentFixture<TmResourceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TmResourceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TmResourceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
