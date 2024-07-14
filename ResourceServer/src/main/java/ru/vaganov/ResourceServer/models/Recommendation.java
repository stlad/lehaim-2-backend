package ru.vaganov.ResourceServer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity @Table(name = "recommendations")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "range_min")
    private Double rangeMin;

    @Column(name = "range_max")
    private Double rangeMax;

    @Column(name = "name")
    private String name;
    @Column(name = "conclusion")
    private String conclusion;
    @Column(name = "recommendation")
    private String recommendation;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "first_param_id", referencedColumnName = "id")
    private Parameter firstParam;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "second_param_id", referencedColumnName = "id")
    private Parameter secondParam;
}
