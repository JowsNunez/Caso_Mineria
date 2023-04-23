import { TestBed } from '@angular/core/testing';

import { SemaforoService } from './semaforo.service';

describe('SemaforoService', () => {
  let service: SemaforoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SemaforoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
