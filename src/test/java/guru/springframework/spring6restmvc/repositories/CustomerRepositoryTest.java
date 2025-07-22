package guru.springframework.spring6restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import guru.springframework.spring6restmvc.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository customerRepository;

  @Test
  void testSaveCustomer() {
    Customer customer = customerRepository.save(Customer.builder()
            .name("John Doe")
        .build());

    assertThat(customer.getId()).isNotNull();
  }
}