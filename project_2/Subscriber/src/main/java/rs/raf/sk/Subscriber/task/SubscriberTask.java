package rs.raf.sk.Subscriber.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import rs.raf.sk.Subscriber.domain.City;
import rs.raf.sk.Subscriber.domain.Subscription;
import rs.raf.sk.Subscriber.domain.User;
import rs.raf.sk.Subscriber.domain.dao.CityDao;
import rs.raf.sk.Subscriber.domain.dao.SubscriptionDao;
import rs.raf.sk.Subscriber.domain.dao.UserDao;
import rs.raf.sk.Subscriber.domain.dto.CityDto;
import rs.raf.sk.Subscriber.domain.dto.MessageDto;
import rs.raf.sk.Subscriber.domain.dto.WeatherDto;
import rs.raf.sk.Subscriber.service.Publisher;
import rs.raf.sk.Subscriber.service.feign.WeatherClient;

import java.util.List;
import java.util.Optional;

public class SubscriberTask {

    @Autowired
    private WeatherDto weatherDto;

    private final UserDao userDao;

    private final Publisher publisher;

    private final SubscriptionDao subDao;

    private final CityDao cityDao;

    private final WeatherClient client;

    @Scheduled(fixedRate = 3600000)
    public void sendAll() {
        List<CityDto> cities = client.fetchCities();

        for(CityDto c : cities){
            Optional<City> city = cityDao.findByName(c.getName());
            WeatherDto weather = client.fetchByCity(city.get().getName());
            List<Subscription> sub = subDao.findByCityid(city.get().getId());
            for(Subscription s : sub){
                Optional<User> user = userDao.findById(s.getUserid());
                publisher.produceMsg(new MessageDto(user.get().getEmail(), "Weather notification", weather));
            }
        }
    }
}
