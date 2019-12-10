package rs.raf.sk.fetcher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.raf.sk.fetcher.domain.Weather;
import rs.raf.sk.fetcher.domain.dao.WeatherDao;
import rs.raf.sk.fetcher.domain.dto.MainDto;
import rs.raf.sk.fetcher.domain.dto.WeatherDto;
import rs.raf.sk.fetcher.service.WeatherService;
import rs.raf.sk.fetcher.service.feign.WeatherClient;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private WeatherDao weatherDao;

    @Override
    public WeatherDto fetchByCity(String city) {
        Weather existing = weatherDao.findByCity(city);

        WeatherDto weather;

        if(existing == null) {
            weather = weatherClient.fetchByCity(city);

            weatherDao.save(Weather.builder()
                    .city(city)
                    .temp(weather.getMain().getTemp())
                    .pressure(weather.getMain().getPressure())
                    .humidity(weather.getMain().getHumidity())
                    .build());
        }else {
            MainDto main = new MainDto();
            main.setTemp(existing.getTemp());
            main.setPressure(existing.getPressure());
            main.setHumidity(existing.getHumidity());

            weather = new WeatherDto();
            weather.setName(existing.getCity());
            weather.setMain(main);
        }

        return weather;
    }
}