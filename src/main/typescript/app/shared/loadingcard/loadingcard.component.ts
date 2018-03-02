import {Component, Input} from '@angular/core';

@Component({
    selector: 'loading-card',
    templateUrl: './loadingcard.component.html'
})
export class LoadingCardComponent {
    @Input() condition: boolean;
}
