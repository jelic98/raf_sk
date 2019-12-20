package rs.raf.sk.Subscriber.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import rs.raf.sk.Subscriber.domain.Subscription;
import rs.raf.sk.Subscriber.domain.User;
import rs.raf.sk.Subscriber.domain.dao.CityDao;
import rs.raf.sk.Subscriber.domain.dao.SubscriptionDao;
import rs.raf.sk.Subscriber.domain.dao.UserDao;
import rs.raf.sk.Subscriber.domain.dto.LoginDto;
import rs.raf.sk.Subscriber.domain.dto.UserReqDto;
import rs.raf.sk.Subscriber.domain.dto.UserResDto;
import rs.raf.sk.Subscriber.service.TokenHandlerService;
import rs.raf.sk.Subscriber.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;


    @Override
    public UserResDto save(UserReqDto dto) {
        Optional<User> optUser = userDao.findByUsername(dto.getUsername());
        if(optUser.isPresent()){
            return null;
        }
        User user = User.builder().username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword()).build();
        userDao.save(user);
        return new UserResDto(user);
    }
}
