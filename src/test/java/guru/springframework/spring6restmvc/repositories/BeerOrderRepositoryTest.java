package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.BeerOrder;
import guru.springframework.spring6restmvc.entities.BeerOrderShipment;
import guru.springframework.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class BeerOrderRepositoryTest {

  @Autowired
  BeerOrderRepository beerOrderRepository;

  @Autowired
  BeerRepository beerRepository;

  @Autowired
  CustomerRepository customerRepository;

  Customer testCustomer;
  Beer testBeer;

  @BeforeEach
  void setUp() {
    testCustomer = customerRepository.findAll().getFirst();
    testBeer = beerRepository.findAll().getFirst();
  }

  @Transactional
  @Test
  void testBeerOrders() {
    BeerOrder beerOrder = BeerOrder.builder()
        .customerRef("Test customer ref")
        .customer(testCustomer)
        .beerOrderShipment(BeerOrderShipment.builder()
            .trackingNumber("12345R")
            .build())
        .build();

    BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);
    System.out.println(savedBeerOrder);
    System.out.println(savedBeerOrder.getCustomerRef());
  }

}