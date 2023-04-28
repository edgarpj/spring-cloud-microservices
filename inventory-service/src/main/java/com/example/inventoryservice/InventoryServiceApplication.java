package com.example.inventoryservice;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public ConnectionFactoryInitializer initializer(final ConnectionFactory connectionFactory) {
        final ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        final CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

    @Bean
    public CommandLineRunner createInventory(final InventoryRepository inventoryRepository) {
        return args -> {
            if (inventoryRepository.findAll().collectList().block().isEmpty()) {
                log.info("Initialization of inventory data started");
                final Resource resource = new ClassPathResource("sample_data.csv");

                final BufferedReader fileReader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
                final CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
                final Random rndNumber = new Random();

                csvParser.getRecords().stream().forEach(csvRecord -> {
                    try {
                        final Inventory inventory = new Inventory();
                        inventory.setUniqueId(csvRecord.get("uniq_id"));
                        final int stock = rndNumber.nextInt(100);
                        inventory.setStock(stock);
                        inventoryRepository.save(inventory).block();
                    } catch (final Exception ex) {
                        log.error("Error trying to save inventory: " + csvRecord.get("uniq_id"));
                    }
                });
                log.info("Initialization of inventory data finished");
            }

        };
    }

}
