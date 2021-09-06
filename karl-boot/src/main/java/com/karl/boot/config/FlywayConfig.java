package com.karl.boot.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Configuration;

/**
 * @author karl
 * @date 2020/06/14
 */
@Configuration
public class FlywayConfig implements FlywayMigrationStrategy {

    @Override
    public void migrate(Flyway flyway) {
        try {
            flyway.migrate();
        } catch (FlywayException e) {
            flyway.repair();
            throw e;
        }
    }
}
