package rs.raf.sk.Subscriber.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rs.raf.sk.Subscriber.domain.dto.CityDto;
import rs.raf.sk.Subscriber.domain.dto.WeatherDto;

import java.util.List;

@FeignClient(name = "weatherClient", url = "localhost:9000/fetcher/")
public interface WeatherClient {

    @GetMapping("/fetchByCity?city={city}")
    WeatherDto fetchByCity(@PathVariable("city") String city);

    @GetMapping("/fetchCities")
    List<CityDto> fetchCities();

}
