package com.deverything.candidate.productstore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;
import java.util.Properties;

@SpringBootApplication
public class ProductStoreApplication {
  private static final Logger LOGGER = LogManager.getLogger();
  private static final String PROPS_FILE_NAME = "application.properties";

  public static Properties applicationProperties() {
    Properties appProps = new Properties();

    try {
      appProps.load(
          Objects
              .requireNonNull(
                  ProductStoreApplication.class
                      .getClassLoader()
                      .getResourceAsStream(PROPS_FILE_NAME)
              )
      );
    } catch (Exception e) {

      LOGGER.error("{} {}", e.getClass().getName(), e.getMessage());
      LOGGER.warn("Properties file with name {} not found.", PROPS_FILE_NAME);
    }

    return appProps;
  }

  public static void main(String[] args) {
    SpringApplication.run(ProductStoreApplication.class, args);
  }
}
