package guru.springframework.spring6restmvc.repositories;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import guru.springframework.spring6restmvc.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, UUIDDeserializer> {

}
