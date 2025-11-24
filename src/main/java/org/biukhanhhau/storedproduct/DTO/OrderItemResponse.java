package org.biukhanhhau.storedproduct.DTO;

import java.math.BigDecimal;

public record OrderItemResponse(String productName,
                                int quantity,
                                BigDecimal totalPrice) {
}
