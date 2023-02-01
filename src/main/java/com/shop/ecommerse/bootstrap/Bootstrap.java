package com.shop.ecommerse.bootstrap;

import com.shop.ecommerse.domain.entity.User;
import com.shop.ecommerse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
//        loadUserData();
    }

    private void loadUserData(){
        User ADMIN =
                User.builder()
                        .password("12345")
                        .username("mikhail")
                        .firstName("mikhail")
                        .lastName("efimov")
                        .email("efim12@gmail.com")
                        .phoneNumber("89318883455")
                        .build();
        User USER =
                User.builder()
                        .password("98776")
                        .username("Anastasia")
                        .firstName("Anastasia")
                        .lastName("Efimova")
                        .email("efimova12@gmail.com")
                        .phoneNumber("89318883422")
                        .build();

            userRepository.save(ADMIN);
            userRepository.save(USER);
    }

}
