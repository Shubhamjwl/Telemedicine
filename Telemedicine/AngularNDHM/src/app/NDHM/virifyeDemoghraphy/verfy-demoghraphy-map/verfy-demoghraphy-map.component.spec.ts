import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerfyDemoghraphyMapComponent } from './verfy-demoghraphy-map.component';

describe('VerfyDemoghraphyMapComponent', () => {
  let component: VerfyDemoghraphyMapComponent;
  let fixture: ComponentFixture<VerfyDemoghraphyMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VerfyDemoghraphyMapComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VerfyDemoghraphyMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
