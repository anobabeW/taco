package com.ano.taco.data;

import com.ano.taco.pojo.Ingredient;
import com.ano.taco.pojo.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;


/**
 * @author wangjiao
 * @version 1.0
 * @date 2021/12/31 15:31
 */
@Repository
public class JdbcTacoRepository implements TacoRepository{
    private JdbcTemplate jdbc;
    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);

        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory preparedStatementCreatorFactory =
                new PreparedStatementCreatorFactory(
                        "insert into Taco (name, createAt) values (?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP);
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc =
                preparedStatementCreatorFactory.newPreparedStatementCreator(
                    Arrays.asList(
                        taco.getName(),
                        new Timestamp(taco.getCreatedAt().getTime())
                ));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc,keyHolder);
        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        jdbc.update("insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
                tacoId, ingredient.getId());

    }
}
