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
                .githubSha(get("Ibex-Build-Github-Sha", "0000000000000000000000000000000000000000"))
                .githubRef(get("Ibex-Build-Github-Ref", "no_ref_specified"))
                .githubTag(get("Ibex-Build-Github-Tag", "no_tag_specified"))
                .githubRunId(Long.parseLong(get("Ibex-Build-Github-Run-Id", "0")))
                .githubRunNumber(Long.parseLong(get("Ibex-Build-Github-Run-Number", "0")))
                .build();
    }

}
