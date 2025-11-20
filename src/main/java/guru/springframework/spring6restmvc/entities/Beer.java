package guru.springframework.spring6restmvc.entities;


import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

  @Id
  @GeneratedValue
  @JdbcTypeCode(SqlTypes.CHAR)
  @Column(length = 36, nullable = false, updatable = false, columnDefinition = "varchar(36)")
  private UUID id;

  @Version
  private Integer version;

  @NotNull
  @NotBlank
  private String beerName;

  @NotNull
  @Enumerated(EnumType.STRING)
  private BeerStyle beerStyle;

  private String upc;
  private Integer quantityOnHand;

  @NotNull
  private BigDecimal price;

  @CreationTimestamp
  private LocalDateTime createdDate;

  @UpdateTimestamp
  private LocalDateTime updatedDate;

  @OneToMany(mappedBy = "beer")
  private Set<BeerOrderLine> beerOrderLines;

  @Builder.Default
  @ManyToMany
  @JoinTable(name = "beer_category",
      joinColumns = @JoinColumn(name = "beer_id"),
      inverseJoinColumns = @JoinColumn(name = "category_id")
  )
  private Set<Category> categories = new LinkedHashSet<>();

  public void addCategory(Category category) {
    this.categories.add(category);
    category.getBeers().add(this);
  }

  public void removeCategory(Category category) {
    this.categories.remove(category);
    category.getBeers().remove(this);
  }
}
