package ru.vaganov.ResourceServer.dto;

import lombok.*;
import ru.vaganov.ResourceServer.models.Patient;

import java.time.LocalDate;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class OncologicalTestDTO {
    private Long id;
    private LocalDate testDate;
}
