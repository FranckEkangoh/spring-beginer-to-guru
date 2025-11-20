package guru.springframework.spring6restmvc.repositories;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import guru.springframework.spring6restmvc.entities.Beer;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("localmysql")
@SpringBootTest
public class MySqlIT {

  @ServiceConnection
  @Container
  static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.4");

  @Autowired
  private BeerRepository beerRepository;


  @Test
  public void testListBeers() {
    List<Beer> beers = beerRepository.findAll();
    assertThat(beers.size()).isGreaterThan(0);
  }
}
