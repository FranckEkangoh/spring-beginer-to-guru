package guru.springframework.spring6restmvc.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import guru.springframework.spring6restmvc.domain.Beer;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
public class MySqlTest {

  @Container
  static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.4");

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
    registry.add("spring.datasource.username", mysqlContainer::getUsername);
    registry.add("spring.datasource.password", mysqlContainer::getPassword);
  }
  
  @Autowired
  DataSource dataSource;

  @Autowired
  private BeerRepository beerRepository;

  @Test
  public void testListBeers() {
    List<Beer> beers = beerRepository.findAll();
    assertThat(beers.size()).isGreaterThan(0);
  }
}
