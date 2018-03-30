package at.hollander.ibex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(
        basePackageClasses = {IbexApplication.class}
)
@SpringBootApplication
@EnableAutoConfiguration
public class IbexApplication {
    public static void main(String[] args) {
        SpringApplication.run(IbexApplication.class, args);
    }
}
