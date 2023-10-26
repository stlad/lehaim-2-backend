package ru.vaganov.ResourceServer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vaganov.ResourceServer.models.Expression;

public interface ExpressionRepo extends JpaRepository<Expression, Long> {

}
