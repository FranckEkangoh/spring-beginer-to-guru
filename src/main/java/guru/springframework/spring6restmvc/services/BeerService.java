package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface BeerService {

  Optional<BeerDTO> getBeerById(UUID id);

  List<BeerDTO> listBeers();

  BeerDTO saveNewBeer(BeerDTO beer);

  BeerDTO updatedBeer(UUID id, BeerDTO beer);

  void deleteBeer(UUID id);
}