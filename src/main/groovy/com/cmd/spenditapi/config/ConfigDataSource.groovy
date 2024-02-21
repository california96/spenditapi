package com.cmd.spenditapi.config
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
@Configuration

class ConfigDataSource {
    //TODO: Replace MySQL creds using application.properties instead.
    @Bean
    static DataSource source() {

        DataSourceBuilder<?> dSB = DataSourceBuilder.create();
        dSB.driverClassName("com.mysql.jdbc.Driver");

        dSB.url("jdbc:mysql://localhost:3306/spenditdb");

        dSB.username("user");

        dSB.password("password");

        dSB.build();
    }
}
