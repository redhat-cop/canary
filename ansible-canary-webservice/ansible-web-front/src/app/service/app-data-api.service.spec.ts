import { TestBed, inject } from '@angular/core/testing';

import { AppDataApiService } from './app-data-api.service';

describe('AppDataApiService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AppDataApiService]
    });
  });

  it('should be created', inject([AppDataApiService], (service: AppDataApiService) => {
    expect(service).toBeTruthy();
  }));
});
