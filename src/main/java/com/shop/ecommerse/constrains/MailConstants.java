package com.shop.ecommerse.constrains;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MailConstants {
    @Value("${spring.mail.host_address}")
    private String hostAddress;
}
