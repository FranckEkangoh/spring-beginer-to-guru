package guru.springframework.spring6restmvc.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CategoryRepositoryTest {

  Beer testBeer;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private BeerRepository beerRepository;

  @BeforeEach
  void setUp() {
    testBeer = beerRepository.findAll().getFirst();
  }

  @Transactional
  @Test
  void testAddCategory() {
    Category savedCat = categoryRepository.save(Category.builder()
        .description("Ales")
        .build());

    testBeer.addCategory(savedCat);
    Beer savedBeer = beerRepository.save(testBeer);
    System.out.println(savedBeer.getBeerName());
    assertThat(savedBeer.getBeerName(), is("Lazy Monk Bohemian Pilsner"));
    assertThat(savedBeer.getCategories().size(), is(1));
  }
}