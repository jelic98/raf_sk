package rs.raf.sk.Subscriber.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import rs.raf.sk.Subscriber.domain.City;
import rs.raf.sk.Subscriber.domain.Subscription;
import rs.raf.sk.Subscriber.domain.User;
import rs.raf.sk.Subscriber.domain.dao.CityDao;
import rs.raf.sk.Subscriber.domain.dao.SubscriptionDao;
import rs.raf.sk.Subscriber.domain.dao.UserDao;
import rs.raf.sk.Subscriber.domain.dto.LoginDto;
import rs.raf.sk.Subscriber.domain.dto.UserResDto;
import rs.raf.sk.Subscriber.service.AuthService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;

    private final SubscriptionDao subDao;

    private final CityDao cityDao;

    @Override
    /*public UserResDto login(LoginDto loginDto) {
        Optional<User> optUser = userDao.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        if(!optUser.isPresent()) {
            return null;
        }
        return new UserResDto(optUser.get());
    }*/

     public UserResDto login(@Validated @RequestBody LoginDto loginDto) {
        Optional<User> optUser = userDao.findByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        if(!optUser.isPresent()) {
            return null;
        }
        List<Subscription> cities = subDao.findByUserid(optUser.get().getId());
        System.out.println(optUser.get().getId() + "");
        if(!cities.isEmpty())
            System.out.println("cool");
        //optUser.get().getCities() = new ArrayList<City>();
        for(Subscription sub : cities){
            optUser.get().getCities().add(cityDao.findById(sub.getCityid()).get());
        }
        System.out.println(optUser.get().getCities() != null);
        return new UserResDto(optUser.get());
     }
}
