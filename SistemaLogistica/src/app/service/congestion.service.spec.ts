import { TestBed } from '@angular/core/testing';

import { CongestionService } from './congestion.service';

describe('CongestionService', () => {
  let service: CongestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CongestionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
