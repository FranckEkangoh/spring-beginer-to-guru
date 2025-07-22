package guru.springframework.spring6restmvc.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

  private UUID id;
  private String name;
  private Integer version;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

}
