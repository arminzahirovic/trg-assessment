import { Component, OnDestroy, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Observable, Subject, takeUntil } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

import { Driver } from 'src/app/model/Driver.model';
import { StateService } from 'src/app/service/state.service';
import { EditDriverModal } from '../modals/edit-driver/edit-driver.component';
import { DriverService } from 'src/app/service/driver.service';
import { ConfirmationModal } from '../modals/confirmation-modal/confirmation-modal.component';

@Component({
  selector: 'trg-drivers-table',
  templateUrl: './drivers-table.component.html',
  styleUrls: ['./drivers-table.component.scss'],
})
export class DriversTableComponent implements OnDestroy {
  drivers: Observable<Driver[]> = this.stateService.drivers$;
  dataSource: MatTableDataSource<Driver>;
  unsubscribeNotifier = new Subject();
  displayedColumns: string[] = [
    'id',
    'name',
    'empty1',
    'empty2',
    'edit',
    'delete',
  ];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private stateService: StateService,
    private dialog: MatDialog,
    private driverService: DriverService,
    public _snackBar: MatSnackBar
  ) {
    this.drivers
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

  editDriver(driver: Driver): void {
    const dialogRef = this.dialog.open(EditDriverModal, {
      data: driver,
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.unsubscribeNotifier))
      .subscribe((editedDriver) => {
        if (editedDriver) {
          this.driverService
            .createDriver(editedDriver)
            .pipe(takeUntil(this.unsubscribeNotifier))
            .subscribe({
              next: (response) => {
                this._snackBar.open(
                  `Driver #${response.id} successfully edited`,
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

  addDriver(): void {
    const dialogRef = this.dialog.open(EditDriverModal);

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.unsubscribeNotifier))
      .subscribe((driver) => {
        if (driver) {
          this.driverService
            .createDriver(driver)
            .pipe(takeUntil(this.unsubscribeNotifier))
            .subscribe({
              next: (response) => {
                this._snackBar.open(
                  `Driver #${response.id} successfully added`,
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

  deleteDriver(driverId: number): void {
    const dialogRef = this.dialog.open(ConfirmationModal);
    dialogRef
      .afterClosed()
      .pipe(takeUntil(this.unsubscribeNotifier))
      .subscribe((data) => {
        if (data) {
          this.driverService
            .deleteDriver(driverId)
            .pipe(takeUntil(this.unsubscribeNotifier))
            .subscribe({
              next: () => {
                this._snackBar.open(
                  `Driver #${driverId} successfully deleted`,
                  undefined,
                  {
                    duration: 3000,
                  }
                );
                this.stateService.reloadData();
              },
              error: (error: HttpErrorResponse) => {
                this._snackBar.open(
                  'You cannot delete assigned driver',
                  undefined,
                  {
                    duration: 3000,
                  }
                );
              },
            });
        }
      });
  }

  ngOnDestroy(): void {
    this.unsubscribeNotifier.complete();
  }
}
