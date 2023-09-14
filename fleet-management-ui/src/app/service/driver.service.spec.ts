import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { DriverService } from './driver.service';
import { Driver } from '../model/Driver.model';

describe('DriverService', () => {
  let service: DriverService;
  let httpTestingController: HttpTestingController;
  const ENDPOINT = 'http://localhost:8080/api/drivers';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DriverService],
    });

    service = TestBed.inject(DriverService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch drivers data', () => {
    const expectedDrivers: Driver[] = [
      { id: 1, name: 'Driver 1' },
      { id: 2, name: 'Driver 2' },
    ];

    service.fetchDriversData().subscribe((drivers) => {
      expect(drivers).toEqual(expectedDrivers);
    });

    const req = httpTestingController.expectOne(ENDPOINT);
    expect(req.request.method).toBe('GET');
    req.flush(expectedDrivers);
    httpTestingController.verify();
  });

  it('should create a driver', () => {
    const newDriver: Driver = { id: 3, name: 'New Driver' };

    service.createDriver(newDriver).subscribe((driver) => {
      expect(driver).toEqual(newDriver);
    });

    const req = httpTestingController.expectOne(ENDPOINT);
    expect(req.request.method).toBe('POST');
    req.flush(newDriver);
    httpTestingController.verify();
  });

  it('should update a driver', () => {
    const updatedDriver: Driver = { id: 1, name: 'Updated Driver' };

    service.updateDriver(updatedDriver).subscribe((driver) => {
      expect(driver).toEqual(updatedDriver);
    });

    const req = httpTestingController.expectOne(ENDPOINT);
    expect(req.request.method).toBe('PUT');
    req.flush(updatedDriver);
    httpTestingController.verify();
  });

  it('should delete a driver', () => {
    const driverIdToDelete = 1;

    service.deleteDriver(driverIdToDelete).subscribe();

    const req = httpTestingController.expectOne(
      `${ENDPOINT}/${driverIdToDelete}`
    );
    expect(req.request.method).toBe('DELETE');
    req.flush({});
    httpTestingController.verify();
  });

  it('should fetch available drivers', () => {
    const includedCarId = 1;
    const expectedDrivers: Driver[] = [
      { id: 1, name: 'Driver 1' },
      { id: 2, name: 'Driver 2' },
    ];

    service.fetchAvailableDrivers(includedCarId).subscribe((drivers) => {
      expect(drivers).toEqual(expectedDrivers);
    });

    const req = httpTestingController.expectOne(
      `${ENDPOINT}/available?includedCarId=${includedCarId}`
    );
    expect(req.request.method).toBe('GET');
    req.flush(expectedDrivers);
    httpTestingController.verify();
  });
});
