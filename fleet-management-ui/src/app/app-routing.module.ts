import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManageFleetComponent } from './manage-fleet/manage-fleet.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { MonitorFleetComponent } from './monitor-fleet/monitor-fleet.component';

const routes: Routes = [
  { path: '', component: ManageFleetComponent },
  { path: 'manage-fleet', component: ManageFleetComponent },
  { path: 'monitor-fleet', component: MonitorFleetComponent },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
