package org.booking;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import org.booking.config.Constants;

/**
 * Point of entry of the application. The SpringBootApplication is a convenient
 * annotation that adds @Configuration and @EnableAutoConfiguration to the class
 * 
 */
@SpringBootApplication
public class Application {
	
	private static final Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setShowBanner(false);
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        addDefaultProfile(app, source);
        Environment env = app.run(args).getEnvironment();
        try {
        	String message = (new StringBuffer())
        			.append("Access URLs:\n----------------------------------------------------------\n\t")
        			.append("Local: \t\thttp://127.0.0.1:")
        			.append(env.getProperty("server.port"))
        			.append("\n\t")
        			.append("External: \thttp://")
        			.append(InetAddress.getLocalHost().getHostAddress())
        			.append(":")
        			.append(env.getProperty("server.port"))
        			.append("\n----------------------------------------------------------")
        			.toString();
			log.info(message);
		} catch (UnknownHostException e) {
			log.error("Unknown Host");
		}
	}
	
    /**
     * If no profile has been configured, set by default the "dev" profile.
     */
    private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
        if (!source.containsProperty("spring.profiles.active") &&
                !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {

            app.setAdditionalProfiles(Constants.SPRING_PROFILE_DEVELOPMENT);
        }
    }

}
