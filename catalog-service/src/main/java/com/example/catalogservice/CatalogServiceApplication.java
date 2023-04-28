package com.example.catalogservice;

import com.example.catalogservice.model.Product;
import com.example.catalogservice.repository.ProductRepository;
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

@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class CatalogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
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
    public CommandLineRunner createProducts(final ProductRepository productRepository) {
        return args -> {
            if (productRepository.findAll().collectList().block().isEmpty()) {
                log.info("Initialization of product data started");
                final Resource resource = new ClassPathResource("sample_data.csv");

                final BufferedReader fileReader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
                final CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

                csvParser.getRecords().stream().forEach(csvRecord -> {
                    try {
                        final Product product = new Product();
                        product.setUniqueId(csvRecord.get("uniq_id"));
                        product.setSku(csvRecord.get("sku"));
                        product.setTitle(csvRecord.get("name_title"));
                        product.setCategory(csvRecord.get("category"));
                        product.setDescription(csvRecord.get("description"));
                        final String price = csvRecord.get("list_price");
                        product.setPrice(price.isEmpty() ? 0 : Double.valueOf(price));

                        productRepository.save(product).block();
                    } catch (final Exception ex) {
                        log.error("Error trying to save product: " + csvRecord.get("uniq_id"));
                    }
                });
                log.info("Initialization of product data finished");
            }

        };
    }
}
