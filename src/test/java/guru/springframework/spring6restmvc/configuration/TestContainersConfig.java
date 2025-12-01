package guru.springframework.spring6restmvc.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@TestConfiguration
@Testcontainers
public class TestContainersConfig {

  @ServiceConnection
  @Container
  static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.4")
      .withDatabaseName("testdb")
      .withUsername("testuser")
      .withPassword("testpass");

  static {
    mysqlContainer.start();
  }

  private JdbcTemplate jdbcTemplate;

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
    registry.add("spring.datasource.username", mysqlContainer::getUsername);
    registry.add("spring.datasource.password", mysqlContainer::getPassword);
    registry.add("spring.datasource.driver-class-name", mysqlContainer::getDriverClassName);
  }

  @BeforeEach
  void resetDatabase() {
    jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0;");
    jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1;");
  }

}
