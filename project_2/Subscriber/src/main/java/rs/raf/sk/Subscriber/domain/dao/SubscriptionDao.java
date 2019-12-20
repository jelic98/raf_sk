package rs.raf.sk.Subscriber.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.sk.Subscriber.domain.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionDao extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUserid(Long userId);
    Optional<Subscription> findByUseridAndCityid(Long userid, Long cityid);
    List<Subscription> findByCityid(Long cityId);
}
