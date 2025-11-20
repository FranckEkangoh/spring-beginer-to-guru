package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerSearchDto;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.specification.BeerSpecification;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

  private static final int DEFAULT_PAGE = 0;
  private static final int DEFAULT_PAGE_SIZE = 25;
  private final BeerRepository beerRepository;
  private final BeerMapper beerMapper;

  @Override
  public Optional<BeerDTO> getBeerById(UUID id) {
    return beerRepository.findById(id)
        .map(beerMapper::beerToBeerDTO);
  }

  @Override
  public Page<BeerDTO> listBeers(String beerName, Boolean showInventory, Integer pageNumber, Integer pageSize) {
    PageRequest page = buildPageRequest(pageNumber, pageSize);
    Page<Beer> pageBeer;

    if (beerName == null) {
      pageBeer = beerRepository.findAll(page);
    } else {
      pageBeer = beerRepository.findByBeerNameIsLikeIgnoreCase("%" + beerName + "%", page);
    }
    if (showInventory != null && !showInventory) {
      pageBeer.forEach(beer -> beer.setQuantityOnHand(null));
    }

    return pageBeer.map(beerMapper::beerToBeerDTO);
  }

  @Override
  public BeerDTO saveNewBeer(BeerDTO beerDto) {
    log.info("Saving new beer in service JPA");
    Beer beer = Beer.builder()
        .beerName(beerDto.getBeerName())
        .beerStyle(beerDto.getBeerStyle())
        .beerName(beerDto.getBeerName())
        .price(beerDto.getPrice())
        .quantityOnHand(beerDto.getQuantityOnHand())
        .createdDate(beerDto.getCreatedDate())
        .updatedDate(beerDto.getUpdatedDate())
        .build();

    return beerMapper.beerToBeerDTO(beerRepository.save(beer));
  }

  @Override
  public BeerDTO updatedBeer(UUID id, BeerDTO beer) {
    return null;
  }

  @Override
  public void deleteBeer(UUID id) {
    beerRepository.findById(id).ifPresent(beer -> {
      log.debug("Deleting beer in service JPA");
      beerRepository.delete(beer);
    });
  }

  @Override
  public Page<BeerDTO> searchBeers(BeerSearchDto beer, Boolean showInventory, Integer pageNumber,
      Integer pageSize) {
    PageRequest page = buildPageRequest(pageNumber, pageSize);
    Page<Beer> beerPage = beerRepository.findAll(BeerSpecification.searchBeer(beer), page);

    if (showInventory != null && !showInventory) {
      beerPage.forEach(b -> b.setQuantityOnHand(null));
    }
    return beerPage.map(beerMapper::beerToBeerDTO);
  }

  public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
    int queryPageNumber;
    int queryPageSize;

    if (pageNumber != null && pageNumber > 0) {
      queryPageNumber = pageNumber;
    } else {
      queryPageNumber = DEFAULT_PAGE;
    }
    if (pageSize == null) {
      queryPageSize = DEFAULT_PAGE_SIZE;
    } else {
      if (pageSize > 1000) {
        queryPageSize = 1000;
      } else {
        queryPageSize = pageSize;
      }
    }

    return PageRequest.of(queryPageNumber, queryPageSize, Sort.by(Sort.Order.asc("beerName")));
  }
}
