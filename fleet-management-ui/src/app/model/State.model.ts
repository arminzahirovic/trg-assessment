import { Car } from "./Car.model";
import { Driver } from "./Driver.model";

export interface State {
    car: Car;
    longitude: number;
    latitude: number;
    speed: number;
    driver: Driver;
}