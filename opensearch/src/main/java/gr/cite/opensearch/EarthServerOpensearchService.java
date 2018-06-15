package gr.cite.opensearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication

/*public class EarthServerOpensearchService {
	public static void main(String[] args) {
		SpringApplication.run(EarthServerOpensearchService.class, args);
	}
}*/

public class EarthServerOpensearchService extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EarthServerOpensearchService.class);
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(EarthServerOpensearchService.class, args);
	}
	
}
