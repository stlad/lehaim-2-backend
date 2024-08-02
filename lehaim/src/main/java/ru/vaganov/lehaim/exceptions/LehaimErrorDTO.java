package ru.vaganov.lehaim.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LehaimErrorDTO {
    private Integer code;
    private String msg;
    private String error;
    private String cause;
}
