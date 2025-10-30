package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.domain.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerSearchDto;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.specification.BeerSpecification;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
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
    return beerRepository.findById(id)
        .map(beerMapper::beerToBeerDTO);
  }

  @Override
  public List<BeerDTO> listBeers() {
    return beerRepository.findAll()
        .stream()
        .map(beerMapper::beerToBeerDTO)
        .collect(Collectors.toList());
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
