package ru.vaganov.lehaim.dto.oncotests;

import lombok.*;

import java.time.LocalDate;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode
public class OncologicalTestDTO {
    private Long id;
    private LocalDate testDate;
}
