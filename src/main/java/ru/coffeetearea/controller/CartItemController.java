package ru.coffeetearea.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.coffeetearea.dto.CartItemDTO;
import ru.coffeetearea.service.CartItemService;

import java.util.List;


@RestController
@RequestMapping("/cart-item")
@RequiredArgsConstructor
@Api(value = "Cart item", tags = {"Cart item"})
public class CartItemController {

    // Fields

    private final CartItemService cartItemService;


    // GET - methods

    /**
     * Получичение списка товаров в корзине
     */
    @GetMapping("/list")
    public List<CartItemDTO> showAllCartDrinks() {

        return cartItemService.showAllCartDrinks();
    }


    // POST - methods

    /**
     * Добавление 1 товара в корзину(1 шт.).
     *
     * @param drinkId
     */
    @PostMapping("/{drinkId}")
    public CartItemDTO addDrinkInCart(@PathVariable Long drinkId) {

        return cartItemService.addDrinkInCart(drinkId);
    }


    // Delete - methods

    /**
     * Удаление товара из корзины.
     *
     * @param drinkId
     */
    @DeleteMapping("/{drinkId}")
    public void removeDrinkFromCart(@PathVariable Long drinkId) {

        cartItemService.removeDrinkFromCart(drinkId);
    }


    /**
     * Удаление всех товаров из корзины.
     */
    @DeleteMapping("/all")
    public void removeAllDrinksFromCart() {

        cartItemService.removeAllDrinksFromCart();
    }
}