import { ComponentFixture, TestBed } from '@angular/core/testing';
import { EditDriverModal } from './edit-driver.component';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('EditDriverModal', () => {
  let fixture: ComponentFixture<EditDriverModal>;
  let component: EditDriverModal;
  let dialogRef: MatDialogRef<EditDriverModal>;
  const mockDriver = {
    id: 1,
    name: 'John Doe',
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditDriverModal],
      imports: [
        ReactiveFormsModule,
        MatDialogModule,
        MatFormFieldModule,
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
          useValue: mockDriver,
        },
      ],
    });

    fixture = TestBed.createComponent(EditDriverModal);
    component = fixture.componentInstance;
    dialogRef = TestBed.inject(MatDialogRef);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with title "Edit driver" for editing', () => {
    fixture.detectChanges();
    expect(component.title).toBe('Edit driver');
  });

  it('should initialize the form with driver data', () => {
    fixture.detectChanges();
    expect(component.form.get('id').value).toBe(mockDriver.id.toString());
    expect(component.form.get('name').value).toBe(mockDriver.name);
  });

  it('should close the dialog when canceled', () => {
    spyOn(dialogRef, 'close');
    component.close();
    expect(dialogRef.close).toHaveBeenCalled();
  });

  it('should save form data and close the dialog', () => {
    const formValue = {
      id: '2',
      name: 'Jane Smith',
    };
    spyOn(dialogRef, 'close');
    component.form.setValue(formValue);
    component.save();
    expect(dialogRef.close).toHaveBeenCalledWith(formValue);
  });
});
