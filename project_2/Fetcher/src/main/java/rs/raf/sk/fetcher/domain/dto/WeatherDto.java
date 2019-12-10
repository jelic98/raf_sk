package rs.raf.sk.fetcher.domain.dto;

import lombok.Data;

@Data
public class WeatherDto {

    private String name;
    private MainDto main;
}