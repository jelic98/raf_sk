package rs.raf.sk.Subscriber.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.raf.sk.Subscriber.domain.City;
import rs.raf.sk.Subscriber.domain.Subscription;
import rs.raf.sk.Subscriber.domain.User;
import rs.raf.sk.Subscriber.domain.dao.CityDao;
import rs.raf.sk.Subscriber.domain.dao.SubscriptionDao;
import rs.raf.sk.Subscriber.domain.dao.UserDao;
import rs.raf.sk.Subscriber.domain.dto.UserReqDto;
import rs.raf.sk.Subscriber.service.SubService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubServiceImpl implements SubService {

    private final UserDao userDao;

    private final SubscriptionDao subDao;

    private final CityDao cityDao;

    @Override
    public List<String> subscribe(String city, UserReqDto dto) {
        Optional<User> user = userDao.findByUsername(dto.getUsername());
        Optional<City> cy = cityDao.findByName(city);
        Subscription sub = Subscription.builder().userid(user.get().getId()).cityid(cy.get().getId()).build();

        subDao.save(sub);
        return showUsers(city);
    }

    @Override
    public List<String> unsubscribe(String city, UserReqDto dto) {
        Optional<User> user = userDao.findByUsername(dto.getUsername());
        Optional<City> cy = cityDao.findByName(city);
        Optional<Subscription> sub = subDao.findByUseridAndCityid(user.get().getId(), cy.get().getId());

        subDao.delete(sub.get());
        return showUsers(city);
    }


    @Override
    public List<String> showUsers(String city) {
        List<String> users = new ArrayList<>();

        Optional<City> c = cityDao.findByName(city);
        List<Subscription> subs = subDao.findByCityid(c.get().getId());
        for(Subscription sub : subs)
            users.add(userDao.findById(sub.getUserid()).get().getUsername());

        return users;
    }


}
