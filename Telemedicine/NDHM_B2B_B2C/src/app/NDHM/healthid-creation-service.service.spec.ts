import { TestBed } from '@angular/core/testing';

import { HealthidCreationServiceService } from './healthid-creation-service.service';

describe('HealthidCreationServiceService', () => {
  let service: HealthidCreationServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HealthidCreationServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
