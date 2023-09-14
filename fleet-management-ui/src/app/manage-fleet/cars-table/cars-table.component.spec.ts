import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CarsTableComponent } from './cars-table.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CarService } from 'src/app/service/car.service';
import { StateService } from 'src/app/service/state.service';
import { EditCarModal } from '../modals/edit-car/edit-car.component';
import { ConfirmationModal } from '../modals/confirmation-modal/confirmation-modal.component';
import { Observable, of } from 'rxjs';
import { Car } from 'src/app/model/Car.model';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

// Mock component for EditCarModal
@Component({
  selector: 'app-edit-car-modal',
  template: '',
})
class MockEditCarModalComponent {
  @Input() data: any;
}

// Mock component for ConfirmationModal
@Component({
  selector: 'app-confirmation-modal',
  template: '',
})
class MockConfirmationModalComponent {}

describe('CarsTableComponent', () => {
  let fixture: ComponentFixture<CarsTableComponent>;
  let component: CarsTableComponent;
  let matDialog: MatDialog;
  let carService: CarService;
  let stateService: StateService;

  // Mock data for testing
  const mockCars: Car[] = [
    { id: 1, brand: 'Toyota', model: 'Camry', driver: { id: 1, name: 'John' } },
    { id: 2, brand: 'Honda', model: 'Civic', driver: { id: 1, name: 'Alice' } },
  ];

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [
        CarsTableComponent,
        MockEditCarModalComponent,
        MockConfirmationModalComponent,
      ],
      imports: [
        BrowserAnimationsModule,
        MatDialogModule,
        MatSnackBarModule,
        MatSortModule,
        MatTableModule,
        MatPaginatorModule,
        MatIconModule,
        HttpClientTestingModule,
        MatFormFieldModule,
        MatInputModule,
      ],
      providers: [MatSnackBar, CarService, StateService],
    }).compileComponents();

    fixture = TestBed.createComponent(CarsTableComponent);
    component = fixture.componentInstance;
    matDialog = TestBed.inject(MatDialog);
    carService = TestBed.inject(CarService);
    stateService = TestBed.inject(StateService);

    spyOn(carService, 'fetchCarsData').and.returnValue(of(mockCars));
    spyOn(carService, 'createCar').and.returnValue(of(mockCars[0]));
    spyOn(carService, 'updateCar').and.returnValue(of(mockCars[0]));
    spyOn(carService, 'deleteCar').and.returnValue(of(null));

    fixture.detectChanges();
  }));

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should open EditCarModal dialog when editing a car', () => {
    spyOn(matDialog, 'open').and.callThrough();
    const carToEdit: Car = mockCars[0];
    component.editCar(carToEdit);
    expect(matDialog.open).toHaveBeenCalledWith(EditCarModal, {
      data: carToEdit,
    });
  });

  it('should open EditCarModal dialog when adding a car', () => {
    spyOn(matDialog, 'open').and.callThrough();
    component.addCar();
    expect(matDialog.open).toHaveBeenCalledWith(EditCarModal);
  });

  it('should open ConfirmationModal dialog when deleting a car', () => {
    spyOn(matDialog, 'open').and.callThrough();
    const carIdToDelete = 1;
    component.deleteCar(carIdToDelete);
    expect(matDialog.open).toHaveBeenCalledWith(ConfirmationModal);
  });

  it('should handle editing a car successfully', () => {
    spyOn(component._snackBar, 'open').and.callThrough();
    const editedCar: Car = {
      id: 1,
      brand: 'Updated Toyota',
      model: 'Updated Camry',
      driver: { id: 1, name: 'Updated John' },
    };
    spyOn(matDialog, 'open').and.returnValue({
      afterClosed: () => of(editedCar),
    } as any);
    component.editCar(mockCars[0]);
    expect(component._snackBar.open).toHaveBeenCalledWith(
      `Car #${editedCar.id} successfully edited`,
      undefined,
      { duration: 3000 }
    );
  });

  it('should handle adding a car successfully', () => {
    spyOn(component._snackBar, 'open').and.callThrough();
    const newCar: Car = {
      id: 1,
      brand: 'Nissan',
      model: 'Altima',
      driver: { id: 1, name: 'Eve' },
    };
    spyOn(matDialog, 'open').and.returnValue({
      afterClosed: () => of(newCar),
    } as any);
    component.addCar();
    expect(component._snackBar.open).toHaveBeenCalledWith(
      `Car #${newCar.id} successfully added`,
      undefined,
      { duration: 3000 }
    );
  });

  it('should unsubscribe from observables on destroy', () => {
    spyOn(component.unsubscribeNotifier, 'complete').and.callThrough();
    component.ngOnDestroy();
    expect(component.unsubscribeNotifier.complete).toHaveBeenCalled();
  });
});
