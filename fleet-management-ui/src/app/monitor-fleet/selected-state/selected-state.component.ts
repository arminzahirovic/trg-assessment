import { Component, Input } from "@angular/core";

import { State } from "src/app/model/State.model";

@Component({
    selector: 'trg-selected-state',
    templateUrl: './selected-state.component.html',
    styleUrls: ['./selected-state.component.scss']
})
export class SelectedStateComponent {
    @Input() state: State;
}