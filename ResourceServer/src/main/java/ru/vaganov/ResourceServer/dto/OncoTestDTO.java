package ru.vaganov.ResourceServer.dto;

import lombok.Data;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.models.ParameterResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class OncoTestDTO {

    private LocalDate testDate;

    private List<ParameterResult> results;

    public OncoTestDTO(){
        results = new ArrayList<>();
    }
}
