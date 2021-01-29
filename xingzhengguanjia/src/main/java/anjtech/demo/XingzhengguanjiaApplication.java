package anjtech.demo;

import anjtech.demo.config.EmailConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({EmailConfig.class})
public class XingzhengguanjiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(XingzhengguanjiaApplication.class, args);
    }

}
