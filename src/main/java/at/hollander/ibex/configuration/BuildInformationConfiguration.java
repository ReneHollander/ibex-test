package at.hollander.ibex.configuration;

import at.hollander.ibex.pojo.BuildInformation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class BuildInformationConfiguration {

    @Bean
    public BuildInformation buildInformation() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("META-INF/MANIFEST.MF");

        Properties prop = new Properties();
        prop.load(is);

        return BuildInformation.builder()
                .longHash(prop.getProperty("Ibex-Build-Long-Hash", "0000000000000000000000000000000000000000"))
                .shortHash(prop.getProperty("Ibex-Build-Short-Hash", "00000000"))
                .branch(prop.getProperty("Ibex-Build-Branch", "no_branch_supplied"))
                .jobId(Integer.parseInt(prop.getProperty("Ibex-Build-Job-Id", "0")))
                .versionTag(prop.getProperty("Ibex-Build-Version-Tag", "00000000-0"))
                .time(prop.getProperty("Ibex-Build-Time", "1970-01-01T00:00:00Z"))
                .timeMillis(Long.parseLong(prop.getProperty("Ibex-Build-Time-Millis", "0")))
                .build();
    }

}
