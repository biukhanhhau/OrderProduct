package org.biukhanhhau.storedproduct;

import org.biukhanhhau.storedproduct.DTO.OrderRequest;
import org.biukhanhhau.storedproduct.DTO.OrderResponse;
import org.biukhanhhau.storedproduct.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/orders/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse response = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("orders")
    public ResponseEntity<List<OrderResponse>> getAllOrder(){
        List<OrderResponse> responses = orderService.getAllOrder();
        return ResponseEntity.ok(responses);
    }
}
