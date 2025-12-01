package guru.springframework.spring6restmvc.integration;

import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.config.SpringSecConfig;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@Import(SpringSecConfig.class)
@SpringBootTest
class BeerControllerIT {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void testListBeers() throws Exception {
    mockMvc.perform(get("/api/v1/beer")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .with(httpBasic("user", "password")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(3)));
  }

  @Test
  void testGetById() throws Exception {
    mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID())
            .accept(MediaType.APPLICATION_JSON)
            .with(httpBasic("user", "password"))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value((Object) null));
  }

  @Rollback
  @Transactional
  @Test
  void testCreateBeer() throws Exception {
    BeerDTO beerDTO = BeerDTO.builder()
        .beerName("New Beer")
        .beerStyle(BeerStyle.LAGER)
        .price(BigDecimal.TEN)
        .upc("UPC")
        .build();

    mockMvc.perform(post("/api/v1/beer")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .with(httpBasic("user", "password"))
            .content(objectMapper.writeValueAsString(beerDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.beerName", is(beerDTO.getBeerName())));
  }

}
