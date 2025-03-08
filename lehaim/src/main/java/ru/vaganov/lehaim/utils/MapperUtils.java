package ru.vaganov.lehaim.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class MapperUtils {

    /**
     * <ul>
     *     <li>Если date == null - ничего не делается</li>
     *     <li>Если date == "" - установка поля в null</li>
     *     <li>Если date - установка поля в значение date</li>
     * </ul>
     * @param set Функиця - сеттер поля даты
     * @param date
     */
    public static void updateDateField(Consumer<LocalDate> set, String date) {
        if(date == null){
            return;
        }
        set.accept(
                date.isEmpty()
                        ? null
                        : LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
        );
    }
}
