import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { CarService } from './car.service';
import { Car } from '../model/Car.model';

describe('CarService', () => {
  let service: CarService;
  let httpTestingController: HttpTestingController;
  const ENDPOINT = 'http://localhost:8080/api/cars';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CarService],
    });

    service = TestBed.inject(CarService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch cars data', () => {
    const expectedCars: Car[] = [
      {
        id: 1,
        brand: 'Brand 1',
        model: 'Model 1',
        driver: { id: 1, name: 'Driver 1' },
      },
      {
        id: 2,
        brand: 'Brand 2',
        model: 'Model 2',
        driver: { id: 2, name: 'Driver 2' },
      },
    ];

    service.fetchCarsData().subscribe((cars) => {
      expect(cars).toEqual(expectedCars);
    });

    const req = httpTestingController.expectOne(ENDPOINT);
    expect(req.request.method).toBe('GET');
    req.flush(expectedCars);
    httpTestingController.verify();
  });

  it('should create a car', () => {
    const newCar: Car = {
      id: 3,
      brand: 'Brand 3',
      model: 'Model 3',
      driver: { id: 3, name: 'Driver 3' },
    };

    service.createCar(newCar).subscribe((car) => {
      expect(car).toEqual(newCar);
    });

    const req = httpTestingController.expectOne(ENDPOINT);
    expect(req.request.method).toBe('POST');
    req.flush(newCar);
    httpTestingController.verify();
  });

  it('should update a car', () => {
    const updatedCar: Car = {
      id: 1,
      brand: 'Updated Brand',
      model: 'Updated Model',
      driver: { id: 1, name: 'Updated driver' },
    };

    service.updateCar(updatedCar).subscribe((car) => {
      expect(car).toEqual(updatedCar);
    });

    const req = httpTestingController.expectOne(ENDPOINT);
    expect(req.request.method).toBe('PUT');
    req.flush(updatedCar);
    httpTestingController.verify();
  });

  it('should delete a car', () => {
    const carIdToDelete = 1;

    service.deleteCar(carIdToDelete).subscribe();

    const req = httpTestingController.expectOne(`${ENDPOINT}/${carIdToDelete}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
    httpTestingController.verify();
  });
});
