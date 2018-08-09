package com.osbornandrew.personal.finance.server;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.Sets;
import com.osbornandrew.personal.finance.server.transactions.Expense;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ExpenseController {

    @RequestMapping("")
    public Set<Expense> getExpenses() {

        return Sets.newHashSet(
                new Expense("Bell's", 19.45),
                new Expense("Taco Bell", 4.65),
                new Expense("States Golf Course", 10.75),
                new Expense("Gun Lake Casino", 23));
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public String testPostToken(@RequestBody String body,
                                @RequestHeader HttpHeaders headers) {

        String userId, email, name = "", locale;
        String idTokenString = headers.get("token").get(0);

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList("4146647642-k52cqa8q4csm2d866nl51ic4kc6hu5ve.apps.googleusercontent.com"))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                userId = payload.getSubject();
                email = payload.getEmail();
                name = (String) payload.get("name");
                locale = (String) payload.get("locale");
            }

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return name;
    }
}
