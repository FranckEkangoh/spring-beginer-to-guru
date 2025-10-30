package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.exceptions.BadRequestException;
import guru.springframework.spring6restmvc.exceptions.NotFoundException;
import guru.springframework.spring6restmvc.model.BeerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<BeerDTO> handleNotFoundException() {
    System.out.println("Handle NotFoundException - in controller");
    return ResponseEntity.ok(BeerDTO.builder().build());
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<BeerDTO> handleBadRequestException() {
    System.out.println("Handle BadRequestException - in controller");
    return ResponseEntity.badRequest().build();
  }

}
