import {Component, Input} from '@angular/core';

@Component({
    selector: 'loading-card',
    templateUrl: './loadingcard.component.html',
    styleUrls: ['./loadingcard.component.css']
})
export class LoadingCardComponent {
    @Input() condition: boolean;
}
