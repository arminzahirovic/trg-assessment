import { Component, OnDestroy, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Observable, Subject, takeUntil } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

import { Car } from 'src/app/model/Car.model';
import { StateService } from 'src/app/service/state.service';
import { EditCarModal } from '../modals/edit-car/edit-car.component';
import { CarService } from 'src/app/service/car.service';
import { ConfirmationModal } from '../modals/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'trg-cars-table',
  templateUrl: './cars-table.component.html',
  styleUrls: ['./cars-table.component.scss'],
})
export class CarsTableComponent implements OnDestroy {
  loading: Observable<boolean> = this.stateService.loading$;
  cars: Observable<Car[]> = this.stateService.cars$;
  dataSource: MatTableDataSource<Car>;
  unsubscribeNotifier = new Subject();
  displayedColumns: string[] = [
    'id',
    'brand',
    'model',
    'driver',
    'edit',
    'delete',
  ];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private stateService: StateService,
    private dialog: MatDialog,
    private carsService: CarService,
    public _snackBar: MatSnackBar
  ) {
    this.cars
      .pipe(takeUntil(this.unsubscribeNotifier))
      .subscribe((response) => {
        this.dataSource = new MatTableDataSource(response);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  editCar(car: Car): void {
    const dialogRef = this.dialog.open(EditCarModal, {
      data: car,
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.unsubscribeNotifier))
      .subscribe((editedCar) => {
        if (editedCar) {
          this.carsService.updateCar(editedCar).subscribe({
            next: (response) => {
              this._snackBar.open(
                `Car #${response.id} successfully edited`,
                undefined,
                {
                  duration: 3000,
                }
              );
              this.stateService.reloadData();
            },
            error: (error: HttpErrorResponse) => {
              this._snackBar.open(error.message, undefined, {
                duration: 3000,
              });
            },
          });
        }
      });
  }

  addCar(): void {
    const dialogRef = this.dialog.open(EditCarModal);

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.unsubscribeNotifier))
      .subscribe((car) => {
        if (car) {
          this.carsService.createCar(car).subscribe({
            next: (response) => {
              this._snackBar.open(
                `Car #${response.id} successfully added`,
                undefined,
                {
                  duration: 3000,
                }
              );
              this.stateService.reloadData();
            },
            error: (error: HttpErrorResponse) => {
              this._snackBar.open(error.message, undefined, {
                duration: 3000,
              });
            },
          });
        }
      });
  }

  deleteCar(carId: number): void {
    const dialogRef = this.dialog.open(ConfirmationModal);
    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.unsubscribeNotifier))
      .subscribe((data) => {
        if (data) {
          this.carsService.deleteCar(carId).subscribe({
            next: () => {
              this._snackBar.open(
                `Car #${carId} successfully deleted`,
                undefined,
                {
                  duration: 3000,
                }
              );
              this.stateService.reloadData();
            },
            error: (error: HttpErrorResponse) => {
              this._snackBar.open(error.message, undefined, {
                duration: 3000,
              });
            },
          });
        }
      });
  }

  ngOnDestroy(): void {
    this.unsubscribeNotifier.complete();
  }
}
