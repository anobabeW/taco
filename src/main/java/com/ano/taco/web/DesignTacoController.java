package com.ano.taco.web;

import com.ano.taco.data.IngredientRepository;
import com.ano.taco.data.TacoRepository;
import com.ano.taco.pojo.Ingredient;
import com.ano.taco.pojo.Ingredient.Type;
import com.ano.taco.pojo.Order;
import com.ano.taco.pojo.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangjiao
 * @version 1.0
 * @date 2021/12/28 11:26
 */
@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
    private final IngredientRepository ingredientRepository;
    private TacoRepository designRepo;

    @Autowired
    public DesignTacoController (IngredientRepository ingredientRepository,
                                 TacoRepository designRepo) {
        this.ingredientRepository = ingredientRepository;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredient -> ingredients.add(ingredient));
        Ingredient.Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        //TODO
        List<Ingredient> ingredientList = ingredients
                .stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
        return ingredientList;
    }

    @PostMapping
    public String processDesign(@Valid Taco design,
                                Errors errors,
                                @ModelAttribute Order order) {
        if(errors.hasErrors()) {
            return "design";
        }
        Taco saved = designRepo.save(design);
        order.addDesign(saved);
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }
}
