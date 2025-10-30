package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.domain.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerSearchDto;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.specification.BeerSpecification;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

  private final static int DEFAULT_PAGE = 0;
  private final static int DEFAULT_PAGE_SIZE = 25;
  private final BeerRepository beerRepository;
  private final BeerMapper beerMapper;

  @Override
  public Optional<BeerDTO> getBeerById(UUID id) {
    return Optional.empty();
  }

  @Override
  public Page<BeerDTO> listBeers(String beerName) {
    Page<Beer> beerList;
    PageRequest pageRequest = buildPageRequest(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    if (StringUtils.hasText(beerName)) {
      beerList = beerRepository.findByBeerNameIsLikeIgnoreCase("%" + beerName + "%" ,pageRequest);
    } else {
      beerList = beerRepository.findAll(pageRequest);
    }
    return beerList.map(beerMapper::beerToBeerDTO);
  }

  @Override
  public Page<BeerDTO> searchBeers(BeerSearchDto beer, Boolean showInventory, Integer pageNumber,
      Integer pageSize) {
    PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

    return beerRepository.findAll(BeerSpecification.searchBeer(beer), pageRequest)
        .map(beerMapper::beerToBeerDTO);
  }

  @Override
  public BeerDTO saveNewBeer(BeerDTO beer) {
    return null;
  }

  @Override
  public BeerDTO updatedBeer(UUID id, BeerDTO beer) {
    return null;
  }

  @Override
  public void deleteBeer(UUID id) {

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
