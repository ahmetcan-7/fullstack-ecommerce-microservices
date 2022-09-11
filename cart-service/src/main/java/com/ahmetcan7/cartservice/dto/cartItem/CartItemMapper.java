package com.ahmetcan7.cartservice.dto.cartItem;

import com.ahmetcan7.cartservice.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemMapper {
    public CartItem createCartItemRequestToCartItem(CreateCartItemRequest createCartItemRequest){
        return CartItem.builder()
                .name(createCartItemRequest.getName())
                .price(createCartItemRequest.getPrice())
                .productId(createCartItemRequest.getProductId())
                .quantity(createCartItemRequest.getQuantity())
                .build();
    }

}
