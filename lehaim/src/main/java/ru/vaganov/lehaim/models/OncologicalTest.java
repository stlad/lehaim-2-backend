package ru.vaganov.lehaim.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.vaganov.lehaim.patient.entity.Patient;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_oncological_test")
@Entity
public class OncologicalTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate testDate;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "link_patient", referencedColumnName = "id")
    private Patient patientOwner;

    private String testNote;

    @OneToMany(mappedBy = "attachedTest", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<ParameterResult> results;
}
