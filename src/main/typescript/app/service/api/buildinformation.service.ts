import {Injectable} from '@angular/core';
import {ApiClient} from './apiclient.service';
import {BuildInformation} from '../../models/buildinformation.model';
import {
    IBEX_BUILD_BRANCH,
    IBEX_BUILD_JOB_ID,
    IBEX_BUILD_LONG_HASH,
    IBEX_BUILD_SHORT_HASH,
    IBEX_BUILD_TIME,
    IBEX_BUILD_TIME_MILLIS,
    IBEX_BUILD_VERSION_TAG
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
            IBEX_BUILD_LONG_HASH,
            IBEX_BUILD_SHORT_HASH,
            IBEX_BUILD_BRANCH,
            IBEX_BUILD_JOB_ID,
            IBEX_BUILD_VERSION_TAG,
            IBEX_BUILD_TIME,
            IBEX_BUILD_TIME_MILLIS));
    }

}
