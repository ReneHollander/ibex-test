package at.hollander.ibex.pojo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BuildInformation {
    private String githubSha;
    private String githubRef;
    private String githubTag;
    private long githubRunId;
    private long githubRunNumber;
}
