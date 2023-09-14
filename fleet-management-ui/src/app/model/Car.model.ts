import { Driver } from './Driver.model';

export interface Car {
  id?: number;
  brand: string;
  model: string;
  driver: Driver | null;
}
