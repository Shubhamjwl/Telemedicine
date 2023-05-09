import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TmProgramOverviewComponent } from './tm-program-overview.component';

describe('TmProgramOverviewComponent', () => {
  let component: TmProgramOverviewComponent;
  let fixture: ComponentFixture<TmProgramOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TmProgramOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TmProgramOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
