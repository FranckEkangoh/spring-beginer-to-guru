package guru.springframework.spring6restmvc.services;

import static org.assertj.core.api.Assertions.assertThat;

import guru.springframework.spring6restmvc.model.BeerCSVRecord;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

public class BeerCsvServiceImplTest {

  BeerCsvService beerCsvService = new BeerCsvServiceImpl();

  @Test
  void convertCSV() throws FileNotFoundException {
    File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

    List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);

    System.out.println(recs.size());

    assertThat(recs.size()).isGreaterThan(0);
  }
}
