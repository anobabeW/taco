package com.ano.taco.data;

import com.ano.taco.pojo.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wangjiao
 * @version 1.0
 * @date 2021/12/30 16:02
 */
@Repository
public class JdbcIngredientRepository implements IngredientRepository{
    private JdbcTemplate jdbc;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query("select id, name, type from Ingredient",
                this::mapRowToIngredient);

    }

    @Override
    public Ingredient findOne(String id) {
//        return jdbc.queryForObject(
//                "select id, name, type from Ingredient where id = ?",
//                this::mapRowToIngredient, id);
        return jdbc.queryForObject("select id, name, type from Ingredient where id = ?",
                (resultSet, i) -> new Ingredient(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        Ingredient.Type.valueOf(resultSet.getString("type"))
                ), id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update("insert into Ingredient (id, name ,type) values (?,?,?);",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString());
        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type")));
    }
}
