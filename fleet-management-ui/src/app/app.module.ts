import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ManageFleetComponent } from './manage-fleet/manage-fleet.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { CarsTableComponent } from './manage-fleet/cars-table/cars-table.component';
import { DriversTableComponent } from './manage-fleet/drivers-table/drivers-table.component';
import { EditCarModal } from './manage-fleet/modals/edit-car/edit-car.component';
import { EditDriverModal } from './manage-fleet/modals/edit-driver/edit-driver.component';
import { ConfirmationModal } from './manage-fleet/modals/confirmation-modal/confirmation-modal.component';
import { MonitorFleetComponent } from './monitor-fleet/monitor-fleet.component';
import { SelectedStateComponent } from './monitor-fleet/selected-state/selected-state.component';
import { MaterialModule } from './material.module';

@NgModule({
  declarations: [
    AppComponent,
    ManageFleetComponent,
    PageNotFoundComponent,
    CarsTableComponent,
    DriversTableComponent,
    EditCarModal,
    EditDriverModal,
    ConfirmationModal,
    MonitorFleetComponent,
    SelectedStateComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommonModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MaterialModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
