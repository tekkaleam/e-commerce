package com.shop.ecommerce.api;

import com.shop.ecommerce.domain.request.order.PostOrderRequest;
import com.shop.ecommerce.domain.response.order.OrderResponse;
import com.shop.ecommerce.handler.exceptions.InvalidArgumentException;
import com.shop.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
public class OrderController extends ApiController{

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/order")
    public ResponseEntity<List<OrderResponse>> getAll(@RequestParam("page") Integer page,
                                                      @RequestParam("size") Integer pageSize){
        if (Objects.isNull(page) || page < 0) {
            throw new InvalidArgumentException("Invalid page");
        }
        if (Objects.isNull(pageSize) || pageSize < 0) {
            throw new InvalidArgumentException("Invalid pageSize");
        }

        List<OrderResponse> responses = orderService.getAllOrders(page, pageSize);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping(value = "/order/count")
    public ResponseEntity<Integer> getAllCount(){
        return new ResponseEntity<>(orderService.getAllOrdersCount(), HttpStatus.OK);
    }

    @PostMapping(value = "/order")
    public ResponseEntity<OrderResponse> response(@RequestBody @Valid PostOrderRequest request){
        OrderResponse response = orderService.postOrder(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
