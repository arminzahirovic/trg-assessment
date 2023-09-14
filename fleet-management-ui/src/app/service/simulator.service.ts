import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { State } from "../model/State.model";
import { HttpClient } from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class SimulatorService {
    private ENDPOINT = "http://localhost:8081/api/states";

    constructor(private httpClient: HttpClient) {}

    public fetchCarsStates(): Observable<State[]> {
        return this.httpClient.get<State[]>(this.ENDPOINT);
    }
}