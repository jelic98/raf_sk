package rs.raf.sk.fetcher.service;

import rs.raf.sk.fetcher.domain.dto.WeatherDto;

public interface WeatherService {

    WeatherDto fetchByCity(String city);
    void addCity(String city);
}