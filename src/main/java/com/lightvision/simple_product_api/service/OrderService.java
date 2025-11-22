package com.lightvision.simple_product_api.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lightvision.simple_product_api.dto.OrderRequest;
import com.lightvision.simple_product_api.dto.OrderResponse;
import com.lightvision.simple_product_api.entity.Order;
import com.lightvision.simple_product_api.entity.OrderItem;
import com.lightvision.simple_product_api.entity.Product;
import com.lightvision.simple_product_api.entity.User;
import com.lightvision.simple_product_api.repository.OrderRepository;
import com.lightvision.simple_product_api.repository.ProductRepository;
import com.lightvision.simple_product_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // QUAN TRỌNG: @Transactional đảm bảo tính toàn vẹn dữ liệu.
    // Nếu có bất kỳ lỗi nào xảy ra trong hàm này (ví dụ: hết hàng),
    // Mọi thay đổi DB trước đó (như trừ kho) sẽ bị Rollback (hoàn tác) ngay lập tức.
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        
        // 1. Tìm User
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Khởi tạo Order (chưa lưu vội)
        Order order = Order.builder()
                .user(user)
                .orderItems(new ArrayList<>())
                .build();

        BigDecimal totalAmount = BigDecimal.ZERO;

        // 3. Duyệt qua từng sản phẩm khách mua
        for (OrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemRequest.getProductId()));

            // 4. KIỂM TRA TỒN KHO (Business Logic quan trọng)
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Product " + product.getName() + " is out of stock. Available: " + product.getStock());
            }

            // 5. TRỪ KHO
            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product); // Lưu kho mới vào DB

            // 6. Tính tiền & Tạo OrderItem
            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(lineTotal);

            OrderItem orderItem = OrderItem.builder()
                    .order(order) // Gán Order cha
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .priceAtPurchase(product.getPrice()) // Lưu giá tại thời điểm mua
                    .build();

            order.getOrderItems().add(orderItem);
        }

        // 7. Chốt đơn
        order.setTotalAmount(totalAmount);
        
        // Lưu Order. Nhờ CascadeType.ALL ở Entity, nó tự lưu luôn list OrderItems
        Order savedOrder = orderRepository.save(order);

        // 8. Chuyển đổi sang Response DTO để trả về
        return mapToOrderResponse(savedOrder);
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToOrderResponse(order);
    }

    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderResponse.OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(item -> OrderResponse.OrderItemResponse.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .price(item.getPriceAtPurchase())
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }
}