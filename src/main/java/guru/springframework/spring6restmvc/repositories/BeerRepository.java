package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.domain.Beer;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BeerRepository extends JpaRepository<Beer, UUID>, JpaSpecificationExecutor<Beer> {

  Page<Beer> findByBeerNameIsLikeIgnoreCase (String beerName, Pageable pageable);
}
