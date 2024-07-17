package ru.vaganov.ResourceServer.exceptions;

public class RecommendationNotFoundException extends LehaimException {

    private final static String baseMessage = "Не найдена рекомендация: ";

    public RecommendationNotFoundException(Long id) {
        super(baseMessage + "id: " + id.toString());
    }

    @Override
    public Integer getHttpCode() {
        return 404;
    }
}
