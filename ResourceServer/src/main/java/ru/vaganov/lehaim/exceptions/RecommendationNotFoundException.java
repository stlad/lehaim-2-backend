package ru.vaganov.lehaim.exceptions;

import java.util.UUID;

public class RecommendationNotFoundException extends LehaimException {

    private final static String baseMessage = "Не найдена рекомендация: ";

    public RecommendationNotFoundException(UUID id) {
        super(baseMessage + "id: " + id.toString());
    }

    @Override
    public Integer getHttpCode() {
        return 404;
    }
}
