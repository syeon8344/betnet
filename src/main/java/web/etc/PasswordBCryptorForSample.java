package web.etc;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PasswordBCryptorForSample {
    public static void main(String[] args) {
        List<String> passwordList = new ArrayList<>(Arrays.asList(
                "admin",
                "password123",
                "password456",
                "password789",
                "password14561",
                "password15612",
                "password14515",
                "password1233215",
                "password1556",
                "password1561",
                "password35412",
                "password11256",
                "password15451",
                "password1234456",
                "password11565",
                "password123215",
                "password122151",
                "password5345",
                "password11321",
                "password125613",
                "password115136",
                "password125451",
                "password1513",
                "password123133",
                "password122133",
                "password123321",
                "password123215",
                "password123878",
                "password122156",
                "password124523",
                "password1235456",
                "password125458",
                "password125454",
                "password125464",
                "password12448",
                "password123548",
                "password124451",
                "password12451",
                "password12354",
                "password123545",
                "password12556",
                "password12215",
                "password123818",
                "password1232516",
                "password12358",
                "password123546",
                "password12185",
                "password12548",
                "password12365",
                "password123348",
                "password1231231"
        ));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        passwordList.forEach(rawPassword -> {
            String encodedPassword = passwordEncoder.encode(rawPassword);
            System.out.println(encodedPassword);
        });
    }
}
