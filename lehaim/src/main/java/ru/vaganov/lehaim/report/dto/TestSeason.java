package ru.vaganov.lehaim.report.dto;

import java.time.LocalDate;
import java.util.Set;

public enum TestSeason {
    SPRING, AUTUMN;

    public static TestSeason ofDate(LocalDate date){
        if(Set.of(2,3,4,5,6,7).contains(date.getMonthValue()))
            return SPRING;
        return AUTUMN;
    }
}
