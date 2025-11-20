package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.BeerCSVRecord;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import guru.springframework.spring6restmvc.services.BeerCsvService;
import jakarta.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

  private final BeerRepository beerRepository;
  private final CustomerRepository customerRepository;
  private final BeerCsvService beerCsvService;

  @Transactional
  @Override
  public void run(String... args) {
    //loadBeerData();
    loadCsvData();
    loadCustomerData();
  }

  @Transactional
  protected void loadCsvData() {
    if (beerRepository.count() < 10) {
      try {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);
        recs.forEach(beerCsvRecord -> {
          BeerStyle beerStyle = switch (beerCsvRecord.getStyle()) {
            case "American Pale Lager" -> BeerStyle.LAGER;
            case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale",
                 "American Amber / Red Ale", "American Brown Ale" -> BeerStyle.ALE;
            case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
            case "American Porter" -> BeerStyle.PORTER;
            case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
            case "Season / Farmhouse Ale" -> BeerStyle.SAISON;
            case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
            case "English Pale Ale" -> BeerStyle.PALE_ALE;
            default -> BeerStyle.PILSNER;
          };
          beerRepository.save(
              Beer.builder()
                  .beerName(beerCsvRecord.getBeer())
                  .beerStyle(beerStyle)
                  .price(BigDecimal.TEN)
                  .upc(beerCsvRecord.getRow().toString())
                  .quantityOnHand(beerCsvRecord.getCount())
                  .build()
          );
        });
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }
  private void loadCustomerData() {
    if (customerRepository.count() == 0) {
      Customer customer1 = Customer.builder()
          .name("Customer 1")
          .createdDate(LocalDateTime.now())
          .updatedDate(LocalDateTime.now())
          .build();

      Customer customer2 = Customer.builder()
          .name("Customer 2")
          .createdDate(LocalDateTime.now())
          .updatedDate(LocalDateTime.now())
          .build();

      Customer customer3 = Customer.builder()
          .name("Customer 3")
          .createdDate(LocalDateTime.now())
          .updatedDate(LocalDateTime.now())
          .build();

      customerRepository.save(customer1);
      customerRepository.save(customer2);
      customerRepository.save(customer3);
    }
  }

  private void loadBeerData() {
    if (beerRepository.count() == 0) {
      Beer beer1 = Beer.builder()
          .beerName("Galaxy Cat")
          .beerStyle(BeerStyle.PALE_ALE)
          .upc("12356")
          .price(BigDecimal.valueOf(12.99))
          .quantityOnHand(122)
          .createdDate(LocalDateTime.now())
          .updatedDate(LocalDateTime.now())
          .build();

      Beer beer2 = Beer.builder()
          .beerName("Crank")
          .beerStyle(BeerStyle.PALE_ALE)
          .upc("12356222")
          .price(BigDecimal.valueOf(11.99))
          .quantityOnHand(392)
          .createdDate(LocalDateTime.now())
          .updatedDate(LocalDateTime.now())
          .build();

      Beer beer3 = Beer.builder()
          .beerName("Sunshine City")
          .beerStyle(BeerStyle.IPA)
          .upc("12356")
          .price(BigDecimal.valueOf(13.99))
          .quantityOnHand(144)
          .createdDate(LocalDateTime.now())
          .updatedDate(LocalDateTime.now())
          .build();

      beerRepository.save(beer1);
      beerRepository.save(beer2);
      beerRepository.save(beer3);
    }
  }
}
