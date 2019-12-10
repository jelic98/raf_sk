package rs.raf.sk.fetcher.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.sk.fetcher.domain.dto.CityDto;
import rs.raf.sk.fetcher.domain.dto.WeatherDto;
import rs.raf.sk.fetcher.service.WeatherService;

import java.util.List;

@RestController
public class FetcherCtrl {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/fetchByCity")
    public WeatherDto fetchByCity(String city) {
        return weatherService.fetchByCity(city);
    }

    @GetMapping("/fetchCities")
    public List<CityDto> fetchCities() {
        return weatherService.fetchCities();
    }
}