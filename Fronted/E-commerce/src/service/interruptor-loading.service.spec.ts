import { TestBed } from '@angular/core/testing';

import { InterruptorLoadingService } from './interruptor-loading.service';

describe('InterruptorLoadingService', () => {
  let service: InterruptorLoadingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InterruptorLoadingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
