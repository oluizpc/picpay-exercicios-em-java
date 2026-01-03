package picpay_projeto.picpay_project.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Gera hash a partir da senha
    public static String hashPassword(String plainPassword) {
        return encoder.encode(plainPassword);
    }

    // Valida se a senha informada bate com o hash armazenado
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return encoder.matches(plainPassword, hashedPassword);
    }
}
