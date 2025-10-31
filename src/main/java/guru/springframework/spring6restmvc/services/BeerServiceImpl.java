package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerSearchDto;
import guru.springframework.spring6restmvc.model.BeerStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

  private final Map<UUID, BeerDTO> beerMap;

  @Override
  public Page<BeerDTO> searchBeers(BeerSearchDto beer, Boolean showInventory, Integer pageNumber,
      Integer pageSize) {
    return Page.empty();
  }

  public BeerServiceImpl() {
    this.beerMap = new HashMap<>();

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

    beerMap.put(beer1.getId(), beer1);
    beerMap.put(beer2.getId(), beer2);
    beerMap.put(beer3.getId(), beer3);
  }

  @Override
  public Optional<BeerDTO> getBeerById(UUID id) {
    log.debug("Get Beer by Id - in service. Id: {}", id);
    return Optional.of(beerMap.get(id));
  }

  @Override
  public Page<BeerDTO> listBeers(String beerName, Boolean showInventory, Integer pageNumber, Integer pageSize) {
    return new PageImpl<>(new ArrayList<>(beerMap.values()), PageRequest.of(pageNumber, pageSize), beerMap.size());
  }

  @Override
  public BeerDTO saveNewBeer(BeerDTO beer) {
    log.info("Save New Beer - in service without JPA");
    beerMap.put(beer.getId(), beer);
    return beerMap.get(beer.getId());
  }

  @Override
  public BeerDTO updatedBeer(UUID id, BeerDTO beer) {
    BeerDTO existingBeer = beerMap.get(id);
    if (existingBeer != null) {
      Optional.ofNullable(beer.getBeerName()).ifPresent(existingBeer::setBeerName);
      Optional.ofNullable(beer.getUpc()).ifPresent(existingBeer::setUpc);
      Optional.ofNullable(beer.getPrice()).ifPresent(existingBeer::setPrice);
      Optional.ofNullable(beer.getQuantityOnHand()).ifPresent(existingBeer::setQuantityOnHand);
      Optional.ofNullable(beer.getVersion()).ifPresent(existingBeer::setVersion);
      Optional.ofNullable(beer.getBeerStyle()).ifPresent(existingBeer::setBeerStyle);
      existingBeer.setUpdatedDate(LocalDateTime.now());
    }
    return beerMap.put(id, beer);
  }

  @Override
  public void deleteBeer(UUID id) {
    beerMap.remove(id);
  }
}