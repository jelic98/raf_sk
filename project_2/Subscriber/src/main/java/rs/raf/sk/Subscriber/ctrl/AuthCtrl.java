package rs.raf.sk.Subscriber.ctrl;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rs.raf.sk.Subscriber.domain.dto.LoginDto;
import rs.raf.sk.Subscriber.domain.dto.UserReqDto;
import rs.raf.sk.Subscriber.domain.dto.UserResDto;
import rs.raf.sk.Subscriber.service.AuthService;
import rs.raf.sk.Subscriber.service.TokenHandlerService;
import rs.raf.sk.Subscriber.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthCtrl {

    private final AuthService authService;

    private final UserService userService;

    private final TokenHandlerService tokenHanlderService;

    @PostMapping("/login")
    public UserResDto login(@Validated @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/register")
    public UserResDto register(@Validated @RequestBody UserReqDto dto){
        return userService.save(dto);
    }

    @GetMapping("/getToken")
    public String getToken(String email) {
        return tokenHanlderService.getTokenByEmail(email);
    }

    @GetMapping("/getEmail")
    public String getEmail(HttpServletRequest request) {
        String token = request.getHeader("AUTH");
        System.out.println("TOkEN: "+token);
        return tokenHanlderService.getEmailByToken(token);
    }

}
