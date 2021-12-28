package com.ano.taco.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author wangjiao
 * @version 1.0
 * @date 2021/12/28 11:31
 */
@Data
public class Taco {
    @NotNull
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<String> ingredients;

}
