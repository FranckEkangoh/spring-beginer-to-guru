package guru.springframework.spring6restmvc.specification;


import guru.springframework.spring6restmvc.domain.Beer;
import guru.springframework.spring6restmvc.model.BeerSearchDto;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class BeerSpecification {

  public static Specification<Beer> searchBeer(BeerSearchDto beer) {

    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (beer.getBeerName() != null) {
        predicates.add(cb.like(root.get("beerName"), "%" + beer.getBeerName() + "%"));
      }
      if (beer.getBeerStyle() != null) {
        predicates.add(cb.like(root.get("beerStyle").as(String.class), "%" + beer.getBeerStyle() + "%"));
      }
      if (beer.getUpc() != null) {
        predicates.add(cb.equal(root.get("upc"), beer.getUpc()));
      }
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
