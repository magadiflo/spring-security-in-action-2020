package com.magadiflo.book.security.app.services;

import com.magadiflo.book.security.app.entities.Otp;
import com.magadiflo.book.security.app.entities.User;
import com.magadiflo.book.security.app.repositories.OtpRepository;
import com.magadiflo.book.security.app.repositories.UserRepository;
import com.magadiflo.book.security.app.utils.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtpRepository otpRepository;

    public void addUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

    public void auth(User user) {
        Optional<User> optionalUserDB = this.userRepository.findUserByUsername(user.getUsername());
        if (optionalUserDB.isPresent()) {
            User userDB = optionalUserDB.get();
            if (this.passwordEncoder.matches(user.getPassword(), userDB.getPassword())) {
                this.renewOtp(userDB);
            } else {
                throw new BadCredentialsException("Credenciales incorrectos! El password no hace match!");
            }
        } else {
            throw new BadCredentialsException("Credenciales incorrectos! El username " + user.getUsername() + " no existe!");
        }
    }

    public boolean check(Otp otpToValidate) {
        Optional<Otp> otpByUsername = this.otpRepository.findOtpByUsername(otpToValidate.getUsername());
        if (otpByUsername.isPresent()) {
            Otp otpDB = otpByUsername.get();
            return otpToValidate.getCode().equals(otpDB.getCode());
        }
        return false;
    }

    private void renewOtp(User user) {
        String code = GenerateCodeUtil.generateCode();
        Optional<Otp> optionalUserOtpDB = this.otpRepository.findOtpByUsername(user.getUsername());

        if (optionalUserOtpDB.isPresent()) {
            Otp otpDB = optionalUserOtpDB.get();
            otpDB.setCode(code);
        } else {
            Otp otp = new Otp();
            otp.setUsername(user.getUsername());
            otp.setCode(code);
            this.otpRepository.save(otp);
        }
    }
}
