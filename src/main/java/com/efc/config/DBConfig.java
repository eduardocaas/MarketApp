package com.efc.config;

import com.efc.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class DBConfig {

    private final DBService dbService;

    @Autowired
    public DBConfig(DBService dbService) {
        this.dbService = dbService;
    }

    @Bean
    public void loadDB() {
        this.dbService.loadDataDB();
    }
}
