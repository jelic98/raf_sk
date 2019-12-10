package rs.raf.sk.fetcher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.raf.sk.fetcher.domain.Weather;
import rs.raf.sk.fetcher.domain.dao.WeatherDao;
import rs.raf.sk.fetcher.domain.dto.CityDto;
import rs.raf.sk.fetcher.domain.dto.WeatherDto;
import rs.raf.sk.fetcher.service.WeatherService;
import rs.raf.sk.fetcher.service.feign.WeatherClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private WeatherDao weatherDao;

    @Override
    public WeatherDto fetchByCity(String city) {
        return weatherClient.fetchByCity(city);
    }

    @Override
    public void addCity(String city) {
        List<Weather> existing = weatherDao.findByCity(city);

        if(existing == null || existing.isEmpty()) {
            weatherDao.save(Weather.builder().city(city).build());
        }
    }

    @Override
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
}