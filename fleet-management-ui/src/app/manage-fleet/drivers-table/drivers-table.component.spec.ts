import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DriversTableComponent } from './drivers-table.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { DriverService } from 'src/app/service/driver.service';
import { StateService } from 'src/app/service/state.service';
import { EditDriverModal } from '../modals/edit-driver/edit-driver.component';
import { ConfirmationModal } from '../modals/confirmation-modal/confirmation-modal.component';
import { of } from 'rxjs';
import { Driver } from 'src/app/model/Driver.model';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-edit-driver-modal',
  template: '',
})
class MockEditDriverModalComponent {
  @Input() data: any;
}

@Component({
  selector: 'app-confirmation-modal',
  template: '',
})
class MockConfirmationModalComponent {}

describe('DriversTableComponent', () => {
  let fixture: ComponentFixture<DriversTableComponent>;
  let component: DriversTableComponent;
  let matDialog: MatDialog;
  let driverService: DriverService;
  let stateService: StateService;

  const mockDrivers: Driver[] = [
    { id: 1, name: 'John' },
    { id: 2, name: 'Alice' },
  ];

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [
        DriversTableComponent,
        MockEditDriverModalComponent,
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
      providers: [MatSnackBar, DriverService, StateService],
    }).compileComponents();

    fixture = TestBed.createComponent(DriversTableComponent);
    component = fixture.componentInstance;
    matDialog = TestBed.inject(MatDialog);
    driverService = TestBed.inject(DriverService);
    stateService = TestBed.inject(StateService);

    spyOn(driverService, 'createDriver').and.returnValue(of(mockDrivers[0]));
    spyOn(driverService, 'deleteDriver').and.returnValue(of(null));

    fixture.detectChanges();
  }));

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should open EditDriverModal dialog when editing a driver', () => {
    spyOn(matDialog, 'open').and.callThrough();
    const driverToEdit: Driver = mockDrivers[0];
    component.editDriver(driverToEdit);
    expect(matDialog.open).toHaveBeenCalledWith(EditDriverModal, {
      data: driverToEdit,
    });
  });

  it('should open EditDriverModal dialog when adding a driver', () => {
    spyOn(matDialog, 'open').and.callThrough();
    component.addDriver();
    expect(matDialog.open).toHaveBeenCalledWith(EditDriverModal);
  });

  it('should open ConfirmationModal dialog when deleting a driver', () => {
    spyOn(matDialog, 'open').and.callThrough();
    const driverIdToDelete = 1;
    component.deleteDriver(driverIdToDelete);
    expect(matDialog.open).toHaveBeenCalledWith(ConfirmationModal);
  });

  it('should handle adding a driver successfully', () => {
    spyOn(component._snackBar, 'open').and.callThrough();
    const newDriver: Driver = { id: 1, name: 'Eve' };
    spyOn(matDialog, 'open').and.returnValue({
      afterClosed: () => of(newDriver),
    } as any);
    component.addDriver();
    expect(component._snackBar.open).toHaveBeenCalledWith(
      `Driver #${newDriver.id} successfully added`,
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
