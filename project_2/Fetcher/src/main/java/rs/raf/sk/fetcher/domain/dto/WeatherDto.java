package rs.raf.sk.fetcher.domain.dto;

import lombok.Data;
import rs.raf.sk.fetcher.domain.Weather;

@Data
public class WeatherDto {

    private String name;
    private MainDto main;

    public WeatherDto() {

    }

    public WeatherDto(Weather weather) {
        name = weather.getCity();
        main = new MainDto();
        main.setTemp(weather.getTemp());
        main.setPressure(weather.getPressure());
        main.setHumidity(weather.getHumidity());
    }
}