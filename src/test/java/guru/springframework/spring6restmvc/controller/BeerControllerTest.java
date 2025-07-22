package guru.springframework.spring6restmvc.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.services.BeerService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(BeerController.class)
class BeerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BeerService beerService;

  private List<BeerDTO> listOfBeers;


  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    BeerDTO beer1 = BeerDTO.builder()
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

    BeerDTO beer2 = BeerDTO.builder()
        .id(UUID.randomUUID())
        .version(1)
        .beerName("Crank")
        .beerStyle(BeerStyle.PALE_ALE)
        .upc("12356222")
        .price(BigDecimal.valueOf(11.99))
        .quantityOnHand(392)
        .createdDate(LocalDateTime.now())
        .updatedDate(LocalDateTime.now())
        .build();

    BeerDTO beer3 = BeerDTO.builder()
        .id(UUID.randomUUID())
        .version(1)
        .beerName("Sunshine City")
        .beerStyle(BeerStyle.IPA)
        .upc("12356")
        .price(BigDecimal.valueOf(13.99))
        .quantityOnHand(144)
        .createdDate(LocalDateTime.now())
        .updatedDate(LocalDateTime.now())
        .build();
    listOfBeers = Arrays.asList(beer1, beer2, beer3);
  }

  @Test
  void getBeerById() throws Exception {
    BeerDTO beer1 = listOfBeers.getFirst();
    given(beerService.getBeerById(beer1.getId())).willReturn(Optional.of(beer1));
    //Mockito.when(beerService.getBeerById(beer1.getId())).thenReturn(beer1);
    mockMvc.perform(get("/api/v1/beer/" + beer1.getId())
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(beer1.getId().toString())))
        .andExpect(jsonPath("$.version", is(beer1.getVersion())))
        .andExpect(jsonPath("$.beerName", is(beer1.getBeerName())));
  }

  @Test
  void getListOfBeers() throws Exception {
    given(beerService.listBeers()).willReturn(listOfBeers);

    mockMvc.perform(get("/api/v1/beer").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.size()", is(listOfBeers.size())))
        .andExpect(jsonPath("$[0].id", is(listOfBeers.getFirst().getId().toString())))
        .andExpect(jsonPath("$[1].beerName", is(listOfBeers.get(1).getBeerName())));
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

    given(beerService.saveNewBeer(beer)).willReturn(beer);

    mockMvc.perform(post("/api/v1/beer")
            .accept(MediaType.APPLICATION_JSON)
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

    Mockito.when(beerService.getBeerById(any(UUID.class))).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/v1/beer/", UUID.randomUUID()))
        .andExpect(status().isNotFound());
  }
}