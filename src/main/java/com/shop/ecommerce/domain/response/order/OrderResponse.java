package com.shop.ecommerce.domain.response.order;


import com.shop.ecommerce.domain.dto.OrderDetailDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponse(Long id,
                            String phone,
                            String shipAddress,
                            Integer shipped,
                            String trackingNumber,
                            LocalDateTime creationDate,
                            Float totalPrice,
                            String city,
                            String country,
                            List<OrderDetailDTO> list) {
}
