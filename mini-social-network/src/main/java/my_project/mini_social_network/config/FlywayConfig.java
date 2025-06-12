package my_project.mini_social_network.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {
//
//    private Flyway flyway;
//
//    @Bean()
//    public Flyway flyway(DataSource dataSource) {
//        this.flyway = Flyway.configure()
//                .dataSource(dataSource)
//                .baselineOnMigrate(true)
//                .locations("classpath:db/migration")
//                .connectRetries(5)
//                .load();
//        return flyway;
//    }
//
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void runFlyway() {
//        if (flyway != null) {
//            flyway.migrate();
//        }
//    }
}
