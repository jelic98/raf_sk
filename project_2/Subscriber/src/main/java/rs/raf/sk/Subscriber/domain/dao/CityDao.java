package rs.raf.sk.Subscriber.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.sk.Subscriber.domain.City;
import rs.raf.sk.Subscriber.domain.Subscription;

import java.util.List;
import java.util.Optional;

public interface CityDao extends JpaRepository<City, Long> {

    Optional<City> findByName(String name);
}
