package com.osbornandrew.personalfinance;

import lombok.Getter;
import lombok.Setter;

public class User {

    public User(String displayName, String email,
                String socialId, SocialProvider provider) {
        this.displayName = displayName;
        this.email = email;
        this.socialId = socialId;
        this.provider = provider;
    }

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
     * Unique for each provider
     * Google: id
     * Twitter: id_str
     * */
    @Getter @Setter
    private String socialId;
    /**
     * Enum field
     */
    @Getter @Setter
    private SocialProvider provider;
}
