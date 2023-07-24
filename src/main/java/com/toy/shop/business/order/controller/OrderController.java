package com.toy.shop.business.order.controller;

import com.toy.shop.common.ApiResponseDto;
import com.toy.shop.domain.DeliveryStatus;
import com.toy.shop.domain.OrderStatus;
import com.toy.shop.business.order.dto.OrderDto;
import com.toy.shop.business.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Object saveOrder(@RequestBody @Valid OrderDto.Save requestDto) {
        OrderDto.Response responseDto = orderService.saveOrder(requestDto);

        return new ApiResponseDto<>(responseDto);
    }

    @GetMapping
    public Object orders(@RequestParam Long memberId,
                         @RequestParam(required = false) OrderStatus orderStatus,
                         @RequestParam(required = false) DeliveryStatus deliveryStatus,
                         Pageable pageable) {
        Page<OrderDto.Response> list = orderService.orders(memberId, orderStatus, deliveryStatus, pageable);

        return new ApiResponseDto<>(list);
    }

    @GetMapping("/{id}")
    public Object order(@PathVariable Long id) {
        OrderDto.Response responseDto = orderService.order(id);

        return new ApiResponseDto<>(responseDto);
    }

    @PatchMapping("/{id}")
    public Object cancelOrder(@PathVariable Long id) {
        OrderDto.Response responseDto = orderService.cancelOrder(id);

        return new ApiResponseDto<>(responseDto);
    }
}