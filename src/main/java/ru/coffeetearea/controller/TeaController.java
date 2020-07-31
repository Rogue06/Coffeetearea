package ru.coffeetearea.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.coffeetearea.DTO.DrinkDTO;
import ru.coffeetearea.DTO.PageDTO.PageDTO;
import ru.coffeetearea.service.TeaService;

@Api(value = "Tea", tags = {"Tea"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/tea")
public class TeaController {

    private final TeaService teaService;


    // Methods
    //
    // GET - methods
    //

    /**
     * Получение списка товаров
     */
    @GetMapping("/teas")
    public PageDTO<DrinkDTO> getAllCoffees(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "page_size", defaultValue = "5") int pageSize) {

        return teaService.findAll(page, pageSize);
    }

    @GetMapping("/filter/teas")
    public PageDTO<DrinkDTO> getAllCoffees(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "page_size", defaultValue = "5") int pageSize,
                                           @RequestParam String colorName,
                                           @RequestParam String teaType,
                                           @RequestParam String country) {

        return teaService.findAllByFilter(page, pageSize, colorName, teaType, country);
    }
}