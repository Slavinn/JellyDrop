package slavinn.io.jellydrop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class JellydropApplication {

	public static void main(String[] args) {
		SpringApplication.run(JellydropApplication.class, args);
	}

}
