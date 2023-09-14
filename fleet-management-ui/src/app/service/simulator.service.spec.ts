import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { SimulatorService } from './simulator.service';
import { State } from '../model/State.model';

describe('SimulatorService', () => {
  let service: SimulatorService;
  let httpTestingController: HttpTestingController;
  const ENDPOINT = 'http://localhost:8081/api/states';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SimulatorService],
    });

    service = TestBed.inject(SimulatorService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch car states', () => {
    const expectedStates: State[] = [
      {
        car: {
          id: 1,
          brand: 'New Brand',
          model: 'New Model',
          driver: { id: 1, name: 'John' },
        },
        longitude: 40.0,
        latitude: 5.0,
        speed: 40,
        driver: { id: 1, name: 'John' },
      },
      {
        car: {
          id: 2,
          brand: 'New Brand 2',
          model: 'New Model 2',
          driver: { id: 2, name: 'John 2' },
        },
        longitude: 40.0,
        latitude: 5.0,
        speed: 40,
        driver: { id: 2, name: 'John 2' },
      },
      {
        car: {
          id: 3,
          brand: 'New Brand 3',
          model: 'New Model 3',
          driver: { id: 3, name: 'John 3' },
        },
        longitude: 40.0,
        latitude: 5.0,
        speed: 40,
        driver: { id: 3, name: 'John' },
      },
    ];

    service.fetchCarsStates().subscribe((states) => {
      expect(states).toEqual(expectedStates);
    });

    const req = httpTestingController.expectOne(ENDPOINT);
    expect(req.request.method).toBe('GET');
    req.flush(expectedStates);
    httpTestingController.verify();
  });
});
