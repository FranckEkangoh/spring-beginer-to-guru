package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.domain.Beer;
import guru.springframework.spring6restmvc.mappers.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

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
}
