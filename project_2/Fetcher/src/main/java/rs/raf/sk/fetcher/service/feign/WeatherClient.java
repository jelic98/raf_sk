package rs.raf.sk.fetcher.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rs.raf.sk.fetcher.domain.dto.WeatherDto;

@FeignClient(name = "weatherClient", url = "http://api.openweathermap.org/data/2.5")
public interface WeatherClient {

    @GetMapping("/weather?q={city}&units=metric&APPID=18780441c93aa4e503aef8da60024f87")
    WeatherDto fetchByCity(@PathVariable("city") String city);
}