import {Component, forwardRef, Input, ViewChild} from '@angular/core';
import {NgbTypeahead} from "@ng-bootstrap/ng-bootstrap";
import {Observable} from 'rxjs/Observable';
import {debounceTime, distinctUntilChanged, filter, map, merge} from 'rxjs/operators';
import {Subject} from 'rxjs/Subject';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {City} from "../shared/models/city.model";

@Component({
    selector: 'city-search',
    templateUrl: './citysearch.component.html',
    providers: [
        {
            provide: NG_VALUE_ACCESSOR,
            useExisting: forwardRef(() => CitySearchComponent),
            multi: true
        }
    ]
})
export class CitySearchComponent implements ControlValueAccessor {
    registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

    // Function to call when the rating changes.
    onChange = (city: City) => {
    };

    onTouched = () => {
    };

    writeValue(obj: any): void {
        this.cityModel = obj;
        this.onChange(this.cityModel)
    }

    registerOnChange(fn: (city: City) => void): void {
        this.onChange = fn;
    }

    setDisabledState(isDisabled: boolean): void {
        this.disabled = isDisabled;
    }

    @Input()
    placeholder: string = "";

    @Input()
    disabled = false;

    isLoading = true;

    @Input()
    cities: City[] = [];

    public cityModel: City;

    @ViewChild('instance') instance: NgbTypeahead;
    focus$ = new Subject<string>();
    click$ = new Subject<string>();

    citySearch = (text$: Observable<string>) =>
        text$
            .pipe(debounceTime(200))
            .pipe(distinctUntilChanged())
            .pipe(merge(this.focus$))
            .pipe(merge(this.click$.pipe(filter(() => !this.instance.isPopupOpen()))))
            .pipe(map(term => term === '' ? this.cities
                : this.cities.filter(v => v.name.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)));

    cityFormatter = (city: City) => city.postcode + ' ' + city.name;

    selectCity(city: City) {
        if (!this.disabled) {
            this.writeValue(city);
        }
    }
}
