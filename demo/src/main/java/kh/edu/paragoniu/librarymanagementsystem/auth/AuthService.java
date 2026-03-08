package kh.edu.paragoniu.librarymanagementsystem.auth;

import kh.edu.paragoniu.librarymanagementsystem.common.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserAccountRepository userAccountRepository,
                       PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signupUser(UserSignupRequest request) {
        String username = request.username() == null ? "" : request.username().trim();
        String password = request.password() == null ? "" : request.password().trim();

        if (username.length() < 3) {
            throw new BusinessException("Username must be at least 3 characters.");
        }
        if (password.length() < 4) {
            throw new BusinessException("Password must be at least 4 characters.");
        }
        if (userAccountRepository.findByUsername(username).isPresent()) {
            throw new BusinessException("Username already exists.");
        }

        UserAccount account = new UserAccount();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setRole(Role.USER);
        userAccountRepository.save(account);
    }
}
