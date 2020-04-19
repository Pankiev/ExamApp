package pl.exam.app;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ExamAppApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder
                .sources(ExamAppApplication.class)
                .bannerMode(Banner.Mode.CONSOLE);
    }

    public static void main(String[] args) {
        SpringApplication.run(ExamAppApplication.class, args);
    }

}
