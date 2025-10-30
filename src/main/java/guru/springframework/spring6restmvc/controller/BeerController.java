package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.exceptions.NotFoundException;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerSearchDto;
import guru.springframework.spring6restmvc.services.BeerService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/**
 * Created by jt, Spring Framework Guru.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

  private final BeerService beerService;
  private final PagedResourcesAssembler<BeerDTO> assembler;

  @GetMapping("/{id}")
  public BeerDTO getBeerById(@PathVariable UUID id){
    log.debug("Get Beer by Id - in controller");
    return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
  }

  @GetMapping
  public ResponseEntity<PagedModel<EntityModel<BeerDTO>>> getAllBeers(@RequestParam(required = false) String beerName){
    log.debug("Get All Beers");
    Page<BeerDTO> page = beerService.listBeers(beerName);
    PagedModel<EntityModel<BeerDTO>> pagedModel = assembler.toModel(page);
    return ResponseEntity.ok(pagedModel);
  }

  @PostMapping
  public ResponseEntity<BeerDTO> createBeer(@RequestBody BeerDTO beer){
    beer.setId(UUID.randomUUID());
    beer.setCreatedDate(LocalDateTime.now());
    beer.setUpdatedDate(LocalDateTime.now());
    log.debug("Create Beer - in controller");
    beerService.saveNewBeer(beer);

    return ResponseEntity.created(URI.create("/api/v1/beer/" + beer.getId())).body(beer);
  }

  @PutMapping("/{id}")
  public ResponseEntity<BeerDTO> updateBeer(@PathVariable UUID id, @RequestBody BeerDTO beer){
    log.debug("Update Beer - in controller");
    BeerDTO updatedBeer = beerService.updatedBeer(id, beer);
    return new ResponseEntity<>(updatedBeer, HttpStatus.OK);
  }

  @PostMapping("/search")
  public ResponseEntity<Page<BeerDTO>> searchBeers(@RequestBody BeerSearchDto beer,
      @RequestParam(required = false) Boolean showInventory,
      @RequestParam(required = false) Integer pageNumber,
      @RequestParam(required = false) Integer pageSize) {
    log.debug("Search Beer - in controller");
    return ResponseEntity.ok(beerService.searchBeers(beer, showInventory, pageNumber, pageSize));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<BeerDTO> deleteBeer(@PathVariable UUID id){
    log.debug("Delete Beer - in controller");
    beerService.deleteBeer(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
