import { Component, Inject, OnDestroy, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Subject, takeUntil } from "rxjs";

import { Car } from "src/app/model/Car.model";
import { Driver } from "src/app/model/Driver.model";
import { DriverService } from "src/app/service/driver.service";

@Component({
  selector: 'trg-edit-car',
  templateUrl: 'edit-car.component.html',
  styleUrls: ['./edit-car.component.scss']
})
export class EditCarModal implements OnInit, OnDestroy {
  title: string;
  drivers: Driver[];
  form: FormGroup;
  unsubscribeNotifier = new Subject();

  constructor(
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<EditCarModal>,
    private driverService: DriverService,
    @Inject(MAT_DIALOG_DATA) public car: Car
  ) {}

  ngOnInit(): void {
    this.loadData();
    this.setData();
  }

  loadData(): void {
    this.driverService.fetchAvailableDrivers(this.car?.id)
      .pipe(takeUntil(this.unsubscribeNotifier))
      .subscribe(response => {
        this.drivers = response;
      });
  }

  setData(): void {
    this.initForm();
    if (this.car) {      
      this.title = "Edit car";
      this.setFormValues();
      return;
    }

    this.title = "Add new car";
  }

  initForm(): void {
    this.form = this.formBuilder.group({
      id: [],
      brand: [],
      model: [],
      driver: []      
    });
  }

  setFormValues(): void {
    this.form.get('id')?.setValue(this.car.id ? this.car.id.toString() : undefined);
    this.form.get('brand')?.setValue(this.car.brand ? this.car.brand : undefined);
    this.form.get('model')?.setValue(this.car.model ? this.car.model : undefined);
    this.form.get('driver')?.setValue(this.car.driver ? this.car.driver : undefined);
  }

  compareDrivers(driver1: Driver, driver2: Driver): boolean {
      if (driver1 && driver2) {
          return driver1.id === driver2.id;
      }

      return false;
  }

  save(): void {
    this.dialogRef.close(this.form.value);
  }

  close(): void {
      this.dialogRef.close();
  }

  ngOnDestroy(): void {
    this.unsubscribeNotifier.complete();
  }
}
