package at.hollander.ibex.pojo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BuildInformation {
    private String longHash;
    private String shortHash;
    private String branch;
    private int jobId;
    private String versionTag;
    private String time;
    private long timeMillis;
}