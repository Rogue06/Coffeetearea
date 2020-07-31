package ru.coffeetearea.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.coffeetearea.DTO.DrinkDTO;
import ru.coffeetearea.DTO.PageDTO.PageDTO;
import ru.coffeetearea.service.CoffeeService;

@Api(value = "Coffee", tags = {"Coffee"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    private final CoffeeService coffeeService;


    // Methods
    //
    // GET - methods
    //

    /**
     * Получение списка товаров
     */
    @GetMapping("/coffees")
    public PageDTO<DrinkDTO> getAllCoffees(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "page_size", defaultValue = "5") int pageSize) {

        return coffeeService.findAll(page, pageSize);
    }

    @GetMapping("/filter/coffees")
    public PageDTO<DrinkDTO> getAllCoffees(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "page_size", defaultValue = "5") int pageSize,
                                           @RequestParam String roastingName,
                                           @RequestParam String coffeeType,
                                           @RequestParam String country) {

        return coffeeService.findAllFilter(page, pageSize, roastingName, coffeeType, country);
    }
}