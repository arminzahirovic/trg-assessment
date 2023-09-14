import { Component, Inject, OnInit } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { Driver } from "src/app/model/Driver.model";

@Component({
  selector: 'trg-edit-driver',
  templateUrl: 'edit-driver.component.html',
  styleUrls: ['./edit-driver.component.scss']
})
export class EditDriverModal implements OnInit {
  title: string;
  form = this.formBuilder.group({
    id: [''],
    name: ['']   
  });

  constructor(
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<EditDriverModal>,
    @Inject(MAT_DIALOG_DATA) public driver: Driver
  ) {}

  ngOnInit(): void {
    if (this.driver) {
      this.initForm();
      this.title = "Edit driver";
      return;
    }

    this.title = "Add new driver";
  }

  initForm(): void {
    this.form.get('id')?.setValue(this.driver.id ? this.driver.id.toString() : '');
    this.form.get('name')?.setValue(this.driver.name ? this.driver.name : '');
  }

  save(): void {
    this.dialogRef.close(this.form.value);
  }

  close(): void {
      this.dialogRef.close();
  }
}
