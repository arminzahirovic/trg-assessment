import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MonitorFleetComponent } from './monitor-fleet.component';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SimulatorService } from '../service/simulator.service';
import { of, throwError } from 'rxjs';
import { State } from '../model/State.model';
import { Feature } from 'ol';

describe('MonitorFleetComponent', () => {
  let component: MonitorFleetComponent;
  let fixture: ComponentFixture<MonitorFleetComponent>;
  let simulatorService: SimulatorService;
  let snackBar: MatSnackBar;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MonitorFleetComponent],
      imports: [HttpClientTestingModule],
      providers: [SimulatorService, MatSnackBar],
    });

    fixture = TestBed.createComponent(MonitorFleetComponent);
    component = fixture.componentInstance;
    simulatorService = TestBed.inject(SimulatorService);
    snackBar = TestBed.inject(MatSnackBar);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should set selected state', () => {
    const mockState: State = {
      car: {
        id: 1,
        brand: 'New Brand',
        model: 'New Model',
        driver: { id: 1, name: 'John' },
      },
      longitude: 40.0,
      latitude: 5.0,
      speed: 60,
      driver: { id: 2, name: 'John' },
    };
    component.setSelectedState(mockState);

    expect(component.selectedState).toEqual(mockState);
  });

  it('should unsubscribe on destroy', () => {
    spyOn(component.unsubscribeNotifier, 'complete');

    component.ngOnDestroy();

    expect(component.unsubscribeNotifier.complete).toHaveBeenCalled();
  });

  it('should update selected state', () => {
    component.selectedState = {
      car: {
        id: 1,
        brand: 'New Brand',
        model: 'New Model',
        driver: { id: 1, name: 'John' },
      },
      speed: 60,
      longitude: 0,
      latitude: 0,
      driver: { id: 1, name: 'John' },
    };
    component.states = [
      {
        car: {
          id: 1,
          brand: 'New Brand',
          model: 'New Model',
          driver: { id: 2, name: 'Sam' },
        },
        driver: { id: 2, name: 'Sam' },
        speed: 60,
        longitude: 0,
        latitude: 0,
      },
      {
        car: {
          id: 2,
          brand: 'New Brand',
          model: 'New Model',
          driver: { id: 3, name: 'Nick' },
        },
        driver: { id: 3, name: 'Nick' },
        speed: 70,
        longitude: 1,
        latitude: 1,
      },
    ];

    component.updateSelectedState();

    expect(component.selectedState).toEqual({
      car: {
        id: 1,
        brand: 'New Brand',
        model: 'New Model',
        driver: { id: 2, name: 'Sam' },
      },
      driver: { id: 2, name: 'Sam' },
      speed: 60,
      longitude: 0,
      latitude: 0,
    });
  });
});
