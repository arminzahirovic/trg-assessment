import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Driver } from "../model/Driver.model";

@Injectable({
    providedIn: 'root'
})
export class DriverService {
    private ENDPOINT = "http://localhost:8080/api/drivers";

    constructor(private httpClient: HttpClient) {}

    public fetchDriversData(): Observable<Driver[]>{
        return this.httpClient.get<Driver[]>(this.ENDPOINT);
    }

    public createDriver(driver: Driver): Observable<Driver> {
        return this.httpClient.post<Driver>(this.ENDPOINT, driver);
    }

    public updateDriver(driver: Driver): Observable<Driver> {
        return this.httpClient.put<Driver>(this.ENDPOINT, driver);
    }

    public deleteDriver(driverId: number) {
        return this.httpClient.delete(`${this.ENDPOINT}/${driverId}`);
    }

    public fetchAvailableDrivers(includedCarId?: number): Observable<Driver[]> {
        if (includedCarId) {
            return this.httpClient.get<Driver[]>(`${this.ENDPOINT}/available?includedCarId=${includedCarId}`);
        }

        return this.httpClient.get<Driver[]>(`${this.ENDPOINT}/available`);
    }
}