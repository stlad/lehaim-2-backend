package ru.vaganov.ResourceServer.models.recommendations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import ru.vaganov.ResourceServer.dictionary.ChartType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "recommendations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "recommendation_name")
    private String name;
    @Column(name = "conclusion")
    private String conclusion;
    @Column(name = "recommendation")
    private String recommendation;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @Enumerated(EnumType.STRING)
    private ChartType chartType;
}
