package it.gestore_attivita.gestore_attivita.rest.attività.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scala.Int;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatorResponseDto {
    private Integer totalPages;
    private Long totalElements;
}
