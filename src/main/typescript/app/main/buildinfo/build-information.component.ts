import {Component, OnInit} from '@angular/core';
import {BuildInformationService} from "../../service/api/buildinformation.service";
import {BuildInformation} from "../../models/buildinformation.model";

@Component({
    selector: 'buildinformation',
    templateUrl: './buildinformation.component.html'
})
export class BuildInformationComponent implements OnInit {

    isLoading = true;

    backendBuildInformation: BuildInformation;
    frontendBuildInformation: BuildInformation;

    constructor(private buildInformationService: BuildInformationService) {
    }

    async ngOnInit(): void {
        this.isLoading = true;
        this.backendBuildInformation = await this.buildInformationService.getBackendBuildInformation();
        this.frontendBuildInformation = await this.buildInformationService.getFrontendBuildInformation();
        this.isLoading = false;
    }

}
