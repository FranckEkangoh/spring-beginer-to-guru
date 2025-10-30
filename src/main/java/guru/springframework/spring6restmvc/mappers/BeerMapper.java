package guru.springframework.spring6restmvc.mappers;

import guru.springframework.spring6restmvc.domain.Beer;
import guru.springframework.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper (
    componentModel = "spring",
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface BeerMapper {

  //Beer beerDtoToBeer(BeerDTO beerDTO);
  BeerDTO beerToBeerDTO(Beer beer);
}
