import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ConfirmationModal } from './confirmation-modal.component';

describe('ConfirmationModalComponent', () => {
  let fixture: ComponentFixture<ConfirmationModal>;
  let component: ConfirmationModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfirmationModal],
    });

    fixture = TestBed.createComponent(ConfirmationModal);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });
});
