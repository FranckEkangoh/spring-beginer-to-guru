package guru.springframework.spring6restmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@EnableSpringDataWebSupport
@EnableHypermediaSupport(type = HypermediaType.HAL)
@SpringBootApplication
public class Spring6RestMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring6RestMvcApplication.class, args);
    }

}
