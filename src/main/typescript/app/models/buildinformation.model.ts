export class BuildInformation {
    githubSha?: string;
    githubRef?: string;
    githubTag?: string;
    githubRunId?: number;
    githubRunNumber?: number;

    constructor(githubSha: string, githubRef: string, githubTag: string, githubRunId: number, githubRunNumber: number) {
        this.githubSha = githubSha;
        this.githubRef = githubRef;
        this.githubTag = githubTag;
        this.githubRunId = githubRunId;
        this.githubRunNumber = githubRunNumber;
    }
}
