package com.osbornandrew.personalfinance.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.osbornandrew.personalfinance.Budget;
import com.osbornandrew.personalfinance.Wallet;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class User {

    public User() {}

    public User(String displayName, String email,
                String socialId, SocialProvider provider) {
        this.displayName = displayName;
        this.email = email;
        this.socialId = socialId;
        this.provider = provider;
    }

    @Id @GeneratedValue @Getter
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

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    @Getter @Setter
    private Wallet wallet;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    @Getter @Setter
    private Budget budget;
}
