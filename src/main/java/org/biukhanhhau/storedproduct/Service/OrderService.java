package org.biukhanhhau.storedproduct.Service;

import org.biukhanhhau.storedproduct.DTO.OrderItemRequest;
import org.biukhanhhau.storedproduct.DTO.OrderItemResponse;
import org.biukhanhhau.storedproduct.DTO.OrderRequest;
import org.biukhanhhau.storedproduct.DTO.OrderResponse;
import org.biukhanhhau.storedproduct.Model.Order;
import org.biukhanhhau.storedproduct.Model.OrderItem;
import org.biukhanhhau.storedproduct.Model.Product;
import org.biukhanhhau.storedproduct.Repository.OrderRepo;
import org.biukhanhhau.storedproduct.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;
    ProductRepo productRepo;

    public OrderResponse placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        String orderId = "ORD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        order.setCustomerName(order.getCustomerName());
        order.setEmail(order.getEmail());
        order.setOrderId(orderId);
        order.setOrderDate(LocalDate.now());
        order.setStatus("PLACED");

        List<OrderItem> items = new ArrayList<>();
        for (OrderItemRequest itemRequest : orderRequest.items()) {
            Product product = productRepo.findById(itemRequest.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStockQuantity() - itemRequest.quantity() < 0){
                return null;
            }

            product.setStockQuantity(product.getStockQuantity() - itemRequest.quantity());
            productRepo.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity())))
                    .order(order)
                    .build();

            items.add(orderItem);
        }
        order.setItems(items);
        orderRepo.save(order);

        List<OrderItemResponse> itemResponses = new ArrayList<>();
        for(OrderItem iem : items){
            OrderItemResponse itemResponse = new OrderItemResponse(iem.getProduct().getName(), iem.getQuantity(), iem.getTotalPrice());
            itemResponses.add(itemResponse);
        }

        OrderResponse orderResponse = new OrderResponse(order.getOrderId(),
                order.getCustomerName(),
                order.getEmail(),
                order.getStatus(),
                order.getOrderDate(),
                itemResponses );

        return orderResponse;
    }

    public List<OrderResponse> getAllOrder() {
        List<Order> orders = orderRepo.findAll();
        List<OrderItemResponse> responses = new ArrayList<>();


        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders){
            List<OrderItemResponse> itemResponses =new ArrayList<>();
            for (OrderItem item : order.getItems()){
                OrderItemResponse response = new OrderItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getTotalPrice()
                );
                itemResponses.add(response);
            }
            OrderResponse orderResponse = new OrderResponse(
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    itemResponses
            );
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }
}
