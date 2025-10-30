package guru.springframework.spring6restmvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerSearchDto {

  private String beerName;
  private String beerStyle;
  private String upc;
}
