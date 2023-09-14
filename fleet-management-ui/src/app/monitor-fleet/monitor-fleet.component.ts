import { Component, OnDestroy, OnInit } from '@angular/core';
import { Feature } from 'ol';
import Map from 'ol/Map';
import View from 'ol/View';
import { Coordinate } from 'ol/coordinate';
import { Point } from 'ol/geom';
import TileLayer from 'ol/layer/Tile';
import VectorLayer from 'ol/layer/Vector';
import { fromLonLat } from 'ol/proj';
import { OSM } from 'ol/source';
import VectorSource from 'ol/source/Vector';
import Icon from 'ol/style/Icon';
import Style from 'ol/style/Style';
import { Subject, takeUntil, timer } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Car } from '../model/Car.model';
import { Driver } from '../model/Driver.model';
import { State } from '../model/State.model';
import { SimulatorService } from '../service/simulator.service';

@Component({
  selector: 'trg-monitor-fleet',
  templateUrl: 'monitor-fleet.component.html',
  styleUrls: ['monitor-fleet.component.scss'],
})
export class MonitorFleetComponent implements OnInit, OnDestroy {
  map: Map;
  initCoordinate: Coordinate = [-3.701448, 40.437116];
  logo = '/assets/marker.png';
  states: State[] = [];
  features: Feature[] = [];
  icon = new Icon({
    color: '#BADA55',
    crossOrigin: 'anonymous',
    src: this.logo,
    width: 30,
    height: 45,
  });
  vectorLayer = new VectorLayer();
  intervalTimer = timer(0, 10000);
  unsubscribeNotifier = new Subject();
  selectedState: State | undefined;

  constructor(
    private simulatorService: SimulatorService,
    private _snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.initMap();
    this.handleOnMapClick();
    this.intervalTimer
      .pipe(takeUntil(this.unsubscribeNotifier))
      .subscribe(() => this.loadData());
  }

  loadData(): void {
    this.simulatorService
      .fetchCarsStates()
      .pipe(takeUntil(this.unsubscribeNotifier))
      .subscribe({
        next: (response) => {
          this.states = response;
          this.updateSelectedState();
          this.setData();
        },
        error: (error: HttpErrorResponse) => {
          this._snackBar.open(error.message, undefined, {
            duration: 3000,
          });
        },
      });
  }

  updateSelectedState(): void {
    if (this.selectedState) {
      this.selectedState = this.states.find(
        (state) => this.selectedState?.car.id === state.car.id
      );
    }
  }

  setData(): void {
    this.features = [];
    this.states.forEach((state) => {
      const feature = new Feature({
        geometry: new Point(fromLonLat([state.longitude, state.latitude])),
      });
      feature.setProperties(state);
      feature.setStyle(new Style({ image: this.icon }));
      this.features.push(feature);
    });

    this.vectorLayer = new VectorLayer({
      source: new VectorSource({
        features: this.features,
      }),
    });

    this.updateMap();
  }

  initMap(): void {
    this.map = new Map({
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
        this.vectorLayer,
      ],
      target: 'map',
      view: new View({
        center: fromLonLat(this.initCoordinate),
        zoom: 15,
        maxZoom: 18,
      }),
      controls: [],
    });
  }

  updateMap(): void {
    const layers = this.map.getLayers().getArray();
    this.map.setLayers([layers[0], this.vectorLayer]);
  }

  handleOnMapClick(): void {
    this.map.on('click', (event) => {
      const feature = this.map.forEachFeatureAtPixel(
        event.pixel,
        (selectedFeature) => {
          return selectedFeature;
        }
      );

      let selectedState: State | undefined = undefined;

      if (feature) {
        const featureProperties = feature.getProperties();
        selectedState = {} as State;
        selectedState.car = featureProperties['car'] as Car;
        selectedState.driver = featureProperties['driver'] as Driver;
        selectedState.speed = featureProperties['speed'] as number;
        selectedState.longitude = featureProperties['longitude'] as number;
        selectedState.latitude = featureProperties['latitude'] as number;
      }

      this.setSelectedState(selectedState);
    });
  }

  setSelectedState(state?: State): void {
    this.selectedState = state;
  }

  ngOnDestroy(): void {
    this.unsubscribeNotifier.complete();
  }
}
