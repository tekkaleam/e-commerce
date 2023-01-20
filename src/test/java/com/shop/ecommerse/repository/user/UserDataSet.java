package com.shop.ecommerse.repository.user;

import com.shop.ecommerse.domain.entity.Authority;
import com.shop.ecommerse.domain.entity.User;
import com.shop.ecommerse.domain.entity.UserDetails;
import com.shop.ecommerse.domain.entity.UserDetailsId;

public class UserDataSet {

    public static final Authority auth_ADMIN = new Authority(1L, "ADMIN", null);

    public static final Authority auth_USER = new Authority( 21L, "USER", null);

    public static UserDetailsId adminDetailsId = new UserDetailsId();
    public static UserDetailsId userDetailsId = new UserDetailsId();
    static {
        adminDetailsId.setUserId(1L);
        adminDetailsId.setAuthorityId(1L);
        userDetailsId.setUserId(21L);
        userDetailsId.setAuthorityId(21L);
    }
    public static final UserDetails ADMIN_DETAILS = new UserDetails();

    public static final Long ADMIN_ID = 1L;
    public static final Long USER_ID = 21L;

    public static final User ADMIN =
            User.builder()
                    .password("12345")
                    .username("mikhail")
                    .firstName("mikhail")
                    .lastName("efimov")
                    .email("efim12@gmail.com")
                    .phoneNumber("89318883455")
                    .build();
    public static final User USER =
            User.builder()
                    .password("98776")
                    .username("Anastasia")
                    .firstName("Anastasia")
                    .lastName("Efimova")
                    .email("efimova12@gmail.com")
                    .phoneNumber("89318883422")
                    .build();
}
