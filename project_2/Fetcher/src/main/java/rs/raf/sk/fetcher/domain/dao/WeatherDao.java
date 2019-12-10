package rs.raf.sk.fetcher.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.sk.fetcher.domain.Weather;
import java.util.List;

public interface WeatherDao extends JpaRepository<Weather, Long> {

    Weather findByCity(String city);
}