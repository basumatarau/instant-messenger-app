package by.vironit.training.basumatarau.resourceServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EntityScan(basePackages = {"by.vironit.training.basumatarau.messengerService.model"})
@EnableJpaRepositories(basePackages = {"by.vironit.training.basumatarau.messengerService.repository"})
@SpringBootApplication(scanBasePackages =
        {"by.vironit.training.basumatarau.messengerService",
        "by.vironit.training.basumatarau.resourceServer"})
public class ResourceServerStarter {
    public static void main(String[] args) {

        SpringApplication.run(ResourceServerStarter.class, args);
    }
}
