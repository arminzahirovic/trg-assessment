import {
  ComponentFixture,
  TestBed,
  tick,
  fakeAsync,
} from '@angular/core/testing';
import { EditCarModal } from './edit-car.component';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { of } from 'rxjs';
import { Driver } from 'src/app/model/Driver.model';
import { DriverService } from 'src/app/service/driver.service';
import { Car } from 'src/app/model/Car.model';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('EditCarModal', () => {
  let fixture: ComponentFixture<EditCarModal>;
  let component: EditCarModal;
  let dialogRef: MatDialogRef<EditCarModal>;
  let driverService: DriverService;
  const mockCar: Car = {
    id: 1,
    brand: 'Toyota',
    model: 'Camry',
    driver: { id: 1, name: 'John Doe' },
  };
  const mockDrivers: Driver[] = [
    { id: 1, name: 'John Doe' },
    { id: 2, name: 'Jane Smith' },
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditCarModal],
      imports: [
        ReactiveFormsModule,
        MatDialogModule,
        MatFormFieldModule,
        MatOptionModule,
        MatSelectModule,
        MatInputModule,
        BrowserAnimationsModule,
      ],
      providers: [
        FormBuilder,
        {
          provide: MatDialogRef,
          useValue: {
            close: () => {},
          },
        },
        {
          provide: MAT_DIALOG_DATA,
          useValue: mockCar,
        },
        {
          provide: DriverService,
          useValue: {
            fetchAvailableDrivers: () => of(mockDrivers),
          },
        },
      ],
    });

    fixture = TestBed.createComponent(EditCarModal);
    component = fixture.componentInstance;
    dialogRef = TestBed.inject(MatDialogRef);
    driverService = TestBed.inject(DriverService);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with title "Edit car" for editing', () => {
    fixture.detectChanges();
    expect(component.title).toBe('Edit car');
  });

  it('should load available drivers on initialization', fakeAsync(() => {
    spyOn(driverService, 'fetchAvailableDrivers').and.returnValue(
      of(mockDrivers)
    );
    fixture.detectChanges();
    tick(); // Wait for observable to complete
    expect(component.drivers).toEqual(mockDrivers);
  }));

  it('should initialize the form with car data', () => {
    fixture.detectChanges();
    expect(component.form.get('id').value).toBe(mockCar.id.toString());
    expect(component.form.get('brand').value).toBe(mockCar.brand);
    expect(component.form.get('model').value).toBe(mockCar.model);
    expect(component.form.get('driver').value).toBe(mockCar.driver);
  });

  it('should compare drivers correctly', () => {
    const driver1 = { id: 1, name: 'John Doe' };
    const driver2 = { id: 2, name: 'Jane Smith' };
    const driver3 = null;
    expect(component.compareDrivers(driver1, driver2)).toBe(false);
    expect(component.compareDrivers(driver1, driver1)).toBe(true);
    expect(component.compareDrivers(driver1, driver3)).toBe(false);
    expect(component.compareDrivers(driver3, driver3)).toBe(false);
  });

  it('should close the dialog when canceled', () => {
    spyOn(dialogRef, 'close');
    component.close();
    expect(dialogRef.close).toHaveBeenCalled();
  });

  it('should save form data and close the dialog', () => {
    const formValue = {
      id: '2',
      brand: 'Ford',
      model: 'Focus',
      driver: { id: 2, name: 'Jane Smith' },
    };
    spyOn(dialogRef, 'close');
    component.setData();
    component.form.setValue(formValue);
    component.save();
    expect(dialogRef.close).toHaveBeenCalledWith(formValue);
  });

  it('should unsubscribe on destroy', () => {
    spyOn(component.unsubscribeNotifier, 'complete');
    component.ngOnDestroy();
    expect(component.unsubscribeNotifier.complete).toHaveBeenCalled();
  });
});
