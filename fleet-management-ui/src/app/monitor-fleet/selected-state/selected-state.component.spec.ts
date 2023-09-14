import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SelectedStateComponent } from './selected-state.component';
import { State } from 'src/app/model/State.model';

describe('SelectedStateComponent', () => {
  let component: SelectedStateComponent;
  let fixture: ComponentFixture<SelectedStateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SelectedStateComponent],
    });

    fixture = TestBed.createComponent(SelectedStateComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should display the state name', () => {
    const state: State = {
      car: {
        id: 1,
        brand: 'New Brand',
        model: 'New Model',
        driver: { id: 1, name: 'John' },
      },
      longitude: 40.0,
      latitude: 5.0,
      speed: 40,
      driver: { id: 1, name: 'John' },
    };
    component.state = state;
    fixture.detectChanges();

    const element = fixture.nativeElement;
    const carBrand = element.querySelector('.car-brand').textContent;

    expect(carBrand).toContain(state.car.brand);
  });
});
