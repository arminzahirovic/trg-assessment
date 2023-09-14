import {
  ComponentFixture,
  TestBed,
  tick,
  fakeAsync,
} from '@angular/core/testing';
import { ManageFleetComponent } from './manage-fleet.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CarService } from '../service/car.service';
import { DriverService } from '../service/driver.service';
import { StateService } from '../service/state.service';
import { of, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { Car } from '../model/Car.model';
import { Driver } from '../model/Driver.model';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CarsTableComponent } from './cars-table/cars-table.component';
import { MatDialogModule } from '@angular/material/dialog';
import { DriversTableComponent } from './drivers-table/drivers-table.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('ManageFleetComponent', () => {
  let fixture: ComponentFixture<ManageFleetComponent>;
  let component: ManageFleetComponent;
  let carService: CarService;
  let driverService: DriverService;
  let stateService: StateService;
  let snackBar: MatSnackBar;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        ManageFleetComponent,
        CarsTableComponent,
        DriversTableComponent,
      ],
      imports: [
        HttpClientTestingModule,
        MatDialogModule,
        MatFormFieldModule,
        MatPaginatorModule,
        MatProgressSpinnerModule,
        MatTableModule,
        MatInputModule,
        BrowserAnimationsModule,
      ],
      providers: [CarService, DriverService, StateService, MatSnackBar],
    });

    fixture = TestBed.createComponent(ManageFleetComponent);
    component = fixture.componentInstance;
    carService = TestBed.inject(CarService);
    driverService = TestBed.inject(DriverService);
    stateService = TestBed.inject(StateService);
    snackBar = TestBed.inject(MatSnackBar);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch data successfully', fakeAsync(() => {
    const mockCars: Car[] = [
      {
        id: 1,
        brand: 'Toyota',
        model: 'Supra',
        driver: { id: 1, name: 'John Doe' },
      },
    ];
    const mockDrivers: Driver[] = [{ id: 1, name: 'John Doe' }];

    spyOn(carService, 'fetchCarsData').and.returnValue(of(mockCars));
    spyOn(driverService, 'fetchDriversData').and.returnValue(of(mockDrivers));
    spyOn(stateService.loadData$, 'subscribe').and.callThrough();
    spyOn(stateService, 'setLoading');
    spyOn(stateService, 'setCars');
    spyOn(stateService, 'setDrivers');
    spyOn(snackBar, 'open');

    fixture.detectChanges();

    // Trigger the loadData$ subscription
    stateService.reloadData();

    tick(); // Wait for observable to complete

    expect(stateService.setLoading).toHaveBeenCalledWith(true);
    expect(carService.fetchCarsData).toHaveBeenCalled();
    expect(driverService.fetchDriversData).toHaveBeenCalled();
    expect(stateService.setCars).toHaveBeenCalledWith(mockCars);
    expect(stateService.setDrivers).toHaveBeenCalledWith(mockDrivers);
    expect(stateService.setLoading).toHaveBeenCalledWith(false);
    expect(snackBar.open).not.toHaveBeenCalled();
  }));

  it('should handle error while fetching data', fakeAsync(() => {
    const errorResponse = new HttpErrorResponse({
      error: 'Mock error',
      status: 500,
    });

    spyOn(carService, 'fetchCarsData').and.returnValue(
      throwError(errorResponse)
    );
    spyOn(driverService, 'fetchDriversData').and.returnValue(
      throwError(errorResponse)
    );
    spyOn(stateService.loadData$, 'subscribe').and.callThrough();
    spyOn(stateService, 'setLoading');
    spyOn(stateService, 'setCars');
    spyOn(stateService, 'setDrivers');
    spyOn(snackBar, 'open');

    fixture.detectChanges();

    stateService.reloadData();

    tick(); // Wait for observable to complete

    expect(stateService.setLoading).toHaveBeenCalledWith(true);
    expect(carService.fetchCarsData).toHaveBeenCalled();
    expect(driverService.fetchDriversData).toHaveBeenCalled();
    expect(stateService.setCars).not.toHaveBeenCalled();
    expect(stateService.setDrivers).not.toHaveBeenCalled();
    expect(stateService.setLoading).toHaveBeenCalledWith(false);
    expect(snackBar.open).toHaveBeenCalledWith(
      errorResponse.message,
      undefined,
      {
        duration: 3000,
      }
    );
  }));

  it('should unsubscribe on destroy', () => {
    spyOn(component.unsubscribeNotifier, 'complete');
    component.ngOnDestroy();
    expect(component.unsubscribeNotifier.complete).toHaveBeenCalled();
  });
});
