package rs.raf.sk.Subscriber.ctrl;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rs.raf.sk.Subscriber.domain.dto.LoginDto;
import rs.raf.sk.Subscriber.domain.dto.UserReqDto;
import rs.raf.sk.Subscriber.domain.dto.UserResDto;
import rs.raf.sk.Subscriber.service.SubService;
import rs.raf.sk.Subscriber.service.TokenHandlerService;
import rs.raf.sk.Subscriber.service.UserService;
import rs.raf.sk.Subscriber.service.SubService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class SubscriberCtrl {

    private final UserService userService;

    private final TokenHandlerService tokenHandlerService;

    private final SubService subService;

    @GetMapping("/{city}")
    public List<String> subUsers(@PathVariable("city") String city) {
        return subService.showUsers(city);
    }

    @PostMapping("/{city}")
    public List<String> sub(@PathVariable("city") String city, @Validated @RequestBody UserReqDto dto) {
        return subService.subscribe(city, dto);
    }

    @DeleteMapping("/{city}")
    public List<String> unSub(@PathVariable("city") String city, @Validated @RequestBody UserReqDto dto) {
        return subService.unsubscribe(city, dto);
    }
}
