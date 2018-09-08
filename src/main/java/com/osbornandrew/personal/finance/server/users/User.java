package com.osbornandrew.personal.finance.server.users;

import lombok.Getter;
import lombok.Setter;

public class User {

    public User() {}

    @Getter
    private Long id;
    /**
     * Ex: "Andrew Osborn"
     * Google: name
     * Twitter: name
     * */
    @Getter @Setter
    private String displayName;
    /**
     * Google: email
     * Twitter: null -- API doesn't provide email
     */
    @Getter @Setter
    private String email;
    /**
     * Google: id
     * Twitter: id_str
     * */
    @Getter @Setter
    private String socialId;
}
