DELETE FROM recommendations
WHERE id = (SELECT r.id
            FROM regeneration_chart_states rcs
                     INNER JOIN recommendations r ON r.id = rcs.recommendation_id);

DELETE FROM regeneration_chart_states;