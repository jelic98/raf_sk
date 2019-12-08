package rs.raf.sk.fetcher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.raf.sk.fetcher.domain.dto.WeatherDto;
import rs.raf.sk.fetcher.service.WeatherService;
import rs.raf.sk.fetcher.service.feign.WeatherClient;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherClient weatherClient;

    @Override
    public WeatherDto fetchByCity(String city) {
        return weatherClient.fetchByCity(city);
    }
}