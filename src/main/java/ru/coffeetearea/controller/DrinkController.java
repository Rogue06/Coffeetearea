package ru.coffeetearea.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.coffeetearea.DTO.DrinkDTO;
import ru.coffeetearea.DTO.PageDTO.PageDTO;
import ru.coffeetearea.service.DrinkService;

@Api(value = "Drink", tags = {"Drink"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/drink")
public class DrinkController {

    // Fields
    //
    private final DrinkService drinkService;


    // Methods
    //
    // GET - methods
    //
    /** Получение списка товаров */
    @GetMapping("/drinks")
    PageDTO<DrinkDTO> getAllDrinks(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "page_size", defaultValue = "2") int pageSize) {

        return drinkService.getAllDrinks(page, pageSize);
    }
}