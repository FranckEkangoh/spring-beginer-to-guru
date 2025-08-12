package guru.springframework.spring6restmvc.domain;


import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

  @Id
  @GeneratedValue(generator = "UUID")
  @JdbcTypeCode(SqlTypes.CHAR)
  @UuidGenerator
  @Column(length = 36, nullable = false, updatable = false, columnDefinition = "varchar(36)")
  private UUID id;

  @Version
  private Integer version;

  @NotNull
  @NotBlank
  private String beerName;

  @NotNull
  private BeerStyle beerStyle;
  private String upc;
  private Integer quantityOnHand;

  @NotNull
  private BigDecimal price;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
}
