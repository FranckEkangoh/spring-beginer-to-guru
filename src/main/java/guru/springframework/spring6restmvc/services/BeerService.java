package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;

import guru.springframework.spring6restmvc.model.BeerSearchDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface BeerService {

  Optional<BeerDTO> getBeerById(UUID id);

  Page<BeerDTO> listBeers(String beerName, Boolean showInventory, Integer pageNumber, Integer pageSize);

  BeerDTO saveNewBeer(BeerDTO beer);

  BeerDTO updatedBeer(UUID id, BeerDTO beer);

  void deleteBeer(UUID id);

  Page<BeerDTO> searchBeers(BeerSearchDto beer, Boolean showInventory, Integer pageNumber,
      Integer pageSize);
}