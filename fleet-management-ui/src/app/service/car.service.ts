import { Car } from "../model/Car.model";

import { Injectable } from "@angular/core";
import { HttpClient} from "@angular/common/http"
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class CarService {
    private ENDPOINT = "http://localhost:8080/api/cars";

    constructor(private httpClient: HttpClient) {}

    public fetchCarsData(): Observable<Car[]> {
        return this.httpClient.get<Car[]>(this.ENDPOINT);
    }

    public createCar(car: Car): Observable<Car> {
        return this.httpClient.post<Car>(this.ENDPOINT, car);
    }

    public updateCar(car: Car): Observable<Car> {
        return this.httpClient.put<Car>(this.ENDPOINT, car);
    }

    public deleteCar(carId: number): Observable<null> {
        return this.httpClient.delete<null>(`${this.ENDPOINT}/${carId}`);
    }
}
