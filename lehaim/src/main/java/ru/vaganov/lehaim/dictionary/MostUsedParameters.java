package ru.vaganov.lehaim.dictionary;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Часто используемые параметры из каталога БД
 */
@AllArgsConstructor
@Getter
public enum MostUsedParameters {
    NEU(4L),
    LYMF(2L),
    MON(3L),
    PLT(8L),
    CD19(18L),
    CD8(20L),
    CD4(19L),
    CD3(17L),
    IFFy_STIM(36L),
    IFNy_SPON(37L),
    TNFa_STIM(38L),
    TNFa_SPON(39L),
    IL2_STIM(40L),
    IL2_SPON(41L);

    private Long id;
}
