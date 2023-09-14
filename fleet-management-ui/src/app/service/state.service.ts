import { Injectable } from "@angular/core";
import { BehaviorSubject, Subject, forkJoin } from "rxjs";

import { Car } from "../model/Car.model";
import { Driver } from "../model/Driver.model";

@Injectable({
    providedIn: 'root'
})
export class StateService {
    private loading: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    private cars: Subject<Car[]> = new Subject<Car[]>();
    private drivers: Subject<Driver[]> = new Subject<Driver[]>();
    private loadData: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(true);

    loading$ = this.loading.asObservable();
    cars$ = this.cars.asObservable();
    drivers$ = this.drivers.asObservable();
    loadData$ = this.loadData.asObservable();

    setLoading(loading: boolean): void {
        this.loading.next(loading);
    }

    setCars(cars: Car[]): void {
        this.cars.next(cars);
    }

    setDrivers(drivers: Driver[]): void {
        this.drivers.next(drivers);
    }
    
    reloadData(): void {
        this.loadData.next(true);
    }
}