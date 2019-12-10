package rs.raf.sk.fetcher.service;

import rs.raf.sk.fetcher.domain.dto.CityDto;
import rs.raf.sk.fetcher.domain.dto.WeatherDto;

import java.util.List;

public interface WeatherService {

    WeatherDto fetchByCity(String city);
    void addCity(String city);
    List<CityDto> fetchCities();
}