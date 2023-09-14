import { TestBed } from '@angular/core/testing';
import { StateService } from './state.service';
import { Car } from '../model/Car.model';
import { Driver } from '../model/Driver.model';

describe('StateService', () => {
  let service: StateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StateService],
    });

    service = TestBed.inject(StateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should set loading', () => {
    const loadingValue = true;
    service.setLoading(loadingValue);

    service.loading$.subscribe((value) => {
      expect(value).toBe(loadingValue);
    });
  });

  it('should set cars', () => {
    const cars: Car[] = [
      {
        id: 1,
        brand: 'Brand',
        model: 'Model',
        driver: { id: 1, name: 'John' },
      },
    ];
    service.setCars(cars);

    service.cars$.subscribe((value) => {
      expect(value).toEqual(cars);
    });
  });

  it('should set drivers', () => {
    const drivers: Driver[] = [{ id: 1, name: 'Driver 1' }];
    service.setDrivers(drivers);

    service.drivers$.subscribe((value) => {
      expect(value).toEqual(drivers);
    });
  });

  it('should reload data', () => {
    service.reloadData();

    service.loadData$.subscribe((value) => {
      expect(value).toBe(true);
    });
  });
});
