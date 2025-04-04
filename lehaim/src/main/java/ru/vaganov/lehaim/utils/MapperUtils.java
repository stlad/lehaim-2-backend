package ru.vaganov.lehaim.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MapperUtils {
    public static <T> void update(Consumer<T> setter, T object) {
        if (object != null) {
            setter.accept(object);
        }
    }

    /**
     * <ul>
     *     <li>Если date == null - ничего не делается</li>
     *     <li>Если date == "" - установка поля в null</li>
     *     <li>Если date - установка поля в значение date</li>
     * </ul>
     *
     * @param set  Функиця - сеттер поля даты
     * @param date
     */
    public static void update(Consumer<LocalDate> set, String date) {
        if (date == null) {
            return;
        }
        if (date.isEmpty()) {
            set.accept(null);
        } else {
            set.accept(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }

    public static <T> void set(Consumer<T> setter, T object) {
        setter.accept(object);
    }

    public static void set(Consumer<String> setter, LocalDate object) {
        if (object == null) {
            setter.accept(null);
        } else {
            setter.accept(object.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }

    /**
     * Установка фактического значения с проеобразованием String -> LocalDate
     * @param setter Функиця - сеттер поля даты
     * @param object
     */
    public static void set(Consumer<LocalDate> setter, String object) {
        if (object == null || object.isEmpty()) {
            setter.accept(null);
        } else {
            setter.accept(LocalDate.parse(object, DateTimeFormatter.ISO_LOCAL_DATE));
        }
    }
}
