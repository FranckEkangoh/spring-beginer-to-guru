package guru.springframework.spring6restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.bootstrap.BootstrapData;
import guru.springframework.spring6restmvc.config.SpringSecConfig;
import guru.springframework.spring6restmvc.configuration.TestContainersConfig;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureMockMvc
@Testcontainers
@Import({BootstrapData.class, SpringSecConfig.class, TestContainersConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BeerControllerTest {

  @ServiceConnection
  @Container
  static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0");
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private BeerRepository beerRepository;

  static final String USER_NAME = "user";
  static final String  PASSWORD = "password";

  @Transactional
  @BeforeEach
  @Test
  void setUp() {
    assertThat(beerRepository.count()).isEqualTo(2410);
  }

  @Test
  void testListBeersByName() throws Exception {
    mockMvc.perform(get("/api/v1/beer")
            .accept(MediaType.APPLICATION_JSON)
            .with(httpBasic(USER_NAME, PASSWORD))
            .queryParam("beerName", "%IPA%")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.beerDTOList.size()", is(25)))
        .andExpect(jsonPath("$._embedded.beerDTOList[0].quantityOnHand", greaterThan(0)));
  }

  @Test
  void testAllBeers() throws Exception {
    mockMvc.perform(get("/api/v1/beer")
            .accept(MediaType.APPLICATION_JSON)
            .with(httpBasic(USER_NAME, PASSWORD))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.page.totalElements", is(2410)));
  }

  @Test
  void testCreateBeer() throws Exception {
    BeerDTO beer = BeerDTO.builder()
        .id(UUID.randomUUID())
        .version(1)
        .beerName("Galaxy Cat")
        .beerStyle(BeerStyle.PALE_ALE)
        .upc("12356")
        .price(BigDecimal.valueOf(12.99))
        .quantityOnHand(122)
        .createdDate(LocalDateTime.now())
        .updatedDate(LocalDateTime.now())
        .build();

    mockMvc.perform(post("/api/v1/beer")
            .accept(MediaType.APPLICATION_JSON)
            .with(httpBasic(USER_NAME, PASSWORD))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(beer))
        )
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.version", is(beer.getVersion())))
        .andExpect(jsonPath("$.beerName", is(beer.getBeerName())))
        .andExpect(jsonPath("$.beerStyle", is(beer.getBeerStyle().toString())))
        .andExpect(jsonPath("$.upc", is(beer.getUpc())))
        .andExpect(jsonPath("$.price", is(beer.getPrice().doubleValue())));
  }

  @Test
  void getBeerByIdNotFound() throws Exception {
    mockMvc.perform(get("/api/v1/beer/", UUID.randomUUID()))
        .andExpect(status().isNotFound());
  }
}