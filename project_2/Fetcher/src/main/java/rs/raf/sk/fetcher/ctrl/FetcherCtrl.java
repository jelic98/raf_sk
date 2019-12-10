package rs.raf.sk.fetcher.ctrl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.sk.fetcher.domain.Weather;
import rs.raf.sk.fetcher.domain.dao.WeatherDao;
import rs.raf.sk.fetcher.domain.dto.CityDto;
import rs.raf.sk.fetcher.domain.dto.MainDto;
import rs.raf.sk.fetcher.domain.dto.WeatherDto;
import rs.raf.sk.fetcher.service.WeatherService;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FetcherCtrl {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherDao weatherDao;

    @GetMapping("/fetchByCity")
    @HystrixCommand(fallbackMethod = "fetchByCityFallback")
    public WeatherDto fetchByCity(String city) {
        return weatherService.fetchByCity(city);
    }

    @GetMapping("/fetchCities")
    @HystrixCommand(fallbackMethod = "fetchCitiesFallback")
    public List<CityDto> fetchCities() {
        List<CityDto> cities = new ArrayList<>();
        List<Weather> existing = weatherDao.findAll();

        for(Weather weather : existing) {
            CityDto city = new CityDto();
            city.setName(weather.getCity());
            cities.add(city);
        }

        return cities;
    }

    public WeatherDto fetchByCityFallback(String city) {
        MainDto main = new MainDto();
        main.setTemp(0.0);
        main.setPressure(0.0);
        main.setHumidity(0.0);

        WeatherDto weather = new WeatherDto();
        weather.setName(city);
        weather.setMain(main);

        return weather;
    }

    public List<CityDto> fetchCitiesFallback() {
        return new ArrayList<>(0);
    }
}