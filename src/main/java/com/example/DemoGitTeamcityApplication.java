package com.example;

import static java.util.Arrays.asList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoGitTeamcityApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoGitTeamcityApplication.class, args);
    }
}

@Configuration
class BeanConfiguration {

    @Bean(name = "applicationVersion")
    public String version() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        Properties properties = new Properties();

        BufferedReader bufferedReader;
        try (InputStream inputStream = classLoader.getResourceAsStream("META-INF/build-info.properties")) {
            properties.load(inputStream);
            return properties.getProperty("build.version", "NOT DEFINED");
        }
    }
}

@RestController
@RequestMapping("/demo")
class DemoResource {

    @Autowired
    private Environment env;

    @Autowired
    @Qualifier("applicationVersion")
    private String applicationVersion;

    @GetMapping("/profile/active")
    public List<String> env() {
        return asList(env.getActiveProfiles());
    }

    @GetMapping("/applicationVersion")
    public String applicationVersion() {
        return applicationVersion;
    }

}

