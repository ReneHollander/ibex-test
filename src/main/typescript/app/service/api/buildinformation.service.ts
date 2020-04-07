import {Injectable} from '@angular/core';
import {ApiClient} from './apiclient.service';
import {BuildInformation} from '../../models/buildinformation.model';
import {
    IBEX_BUILD_GITHUB_SHA,
    IBEX_BUILD_GITHUB_REF,
    IBEX_BUILD_GITHUB_TAG,
    IBEX_BUILD_GITHUB_RUN_ID,
    IBEX_BUILD_GITHUB_RUN_NUMBER,
} from '../../../version';
import {CachedValue} from '../../util';

@Injectable()
export class BuildInformationService {

    private backendBuildInformationCache: CachedValue<BuildInformation>;

    constructor(private api: ApiClient) {
        this.backendBuildInformationCache = new CachedValue<BuildInformation>(() => this.api.getAndConvert(BuildInformation, '/api/build'), 600);
    }

    async getBackendBuildInformation(): Promise<BuildInformation> {
        return this.backendBuildInformationCache.get();
    }

    async getFrontendBuildInformation(): Promise<BuildInformation> {
        return Promise.resolve(new BuildInformation(
            IBEX_BUILD_GITHUB_SHA,
            IBEX_BUILD_GITHUB_REF,
            IBEX_BUILD_GITHUB_TAG,
            IBEX_BUILD_GITHUB_RUN_ID,
            IBEX_BUILD_GITHUB_RUN_NUMBER,
        ));
    }

}
