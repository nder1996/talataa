import { TestBed } from '@angular/core/testing';

import { ReporteDashboardService } from './reporte-dashboard.service';

describe('ReporteDashboardService', () => {
  let service: ReporteDashboardService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReporteDashboardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
