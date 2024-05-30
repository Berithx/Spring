package com.homework.todo.service;

import com.homework.todo.dto.LoginRequestDto;
import com.homework.todo.dto.SignupRequestDto;
import com.homework.todo.entity.User;
import com.homework.todo.entity.UserRoleEnum;
import com.homework.todo.jwt.JwtUtil;
import com.homework.todo.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, requestDto.getNickname(), requestDto.getPassword(), role);
        userRepository.save(user);
    }

    public boolean verifyUser(LoginRequestDto requestDto) {
        User user = findByUsername(requestDto.getUsername());
        return user != null && user.checkPassword(requestDto.getPassword());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다.")
        );
    }
}
