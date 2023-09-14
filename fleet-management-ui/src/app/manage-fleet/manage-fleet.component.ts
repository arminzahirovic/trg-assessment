import { Component, OnDestroy } from '@angular/core';
import { Subject, forkJoin, takeUntil } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

import { CarService } from '../service/car.service'
import { DriverService } from '../service/driver.service';
import { StateService } from '../service/state.service';


@Component({
  selector: 'trg-manage-fleet',
  templateUrl: './manage-fleet.component.html',
  styleUrls: ['./manage-fleet.component.scss']
})
export class ManageFleetComponent implements OnDestroy {
  unsubscribeNotifier = new Subject();

  constructor(
    private carService: CarService,
    private driverService: DriverService,
    private stateService: StateService,
    private _snackBar: MatSnackBar
  ){
    this.stateService.loadData$.subscribe(() => this.fetchData());
  }

  fetchData(): void {
    this.stateService.setLoading(true);

    forkJoin({
      cars: this.carService.fetchCarsData(),
      drivers: this.driverService.fetchDriversData()
    })
    .pipe(takeUntil(this.unsubscribeNotifier))
    .subscribe({
      next: response => {
        this.stateService.setCars(response.cars);
        this.stateService.setDrivers(response.drivers);
        this.stateService.setLoading(false);
      },
      error: (error: HttpErrorResponse) => {
        this._snackBar.open(error.message, undefined, {
          duration: 3000
        });
        this.stateService.setLoading(false);
      }
    });
  }

  ngOnDestroy(): void {
    this.unsubscribeNotifier.complete();
  }
}
