package at.hollander.ibex.configuration;

import at.hollander.ibex.pojo.BuildInformation;
import com.jcabi.manifests.Manifests;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class BuildInformationConfiguration {

    private static String get(String key, String defaultValue) {
        return Manifests.exists(key) ? Manifests.read(key) : defaultValue;
    }

    @Bean
    public BuildInformation buildInformation() throws IOException {
        return BuildInformation.builder()
                .longHash(get("Ibex-Build-Long-Hash", "0000000000000000000000000000000000000000"))
                .shortHash(get("Ibex-Build-Short-Hash", "00000000"))
                .branch(get("Ibex-Build-Branch", "no_branch_supplied"))
                .jobId(Integer.parseInt(get("Ibex-Build-Job-Id", "0")))
                .versionTag(get("Ibex-Build-Version-Tag", "00000000-0"))
                .time(get("Ibex-Build-Time", "1970-01-01T00:00:00Z"))
                .timeMillis(Long.parseLong(get("Ibex-Build-Time-Millis", "0")))
                .build();
    }

}
