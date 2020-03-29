import { TestBed, inject } from '@angular/core/testing';

import { FactsApiService } from './facts-api.service';

describe('FactsApiService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FactsApiService]
    });
  });

  it('should be created', inject([FactsApiService], (service: FactsApiService) => {
    expect(service).toBeTruthy();
  }));
});
