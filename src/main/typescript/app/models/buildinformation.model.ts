export class BuildInformation {
    longHash?: string;
    shortHash?: string;
    branch?: string;
    jobId?: number;
    versionTag?: string;
    time?: string;
    timeMillis?: number;


    constructor(longHash: string, shortHash: string, branch: string, jobId: number, versionTag: string, time: string, timeMillis: number) {
        this.longHash = longHash;
        this.shortHash = shortHash;
        this.branch = branch;
        this.jobId = jobId;
        this.versionTag = versionTag;
        this.time = time;
        this.timeMillis = timeMillis;
    }
}
