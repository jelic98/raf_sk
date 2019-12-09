package rs.raf.sk.fetcher.domain;

import lombok.*;
import rs.raf.sk.fetcher.domain.dto.WeatherDto;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "weather")
public class Weather {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String city;
    private Double temp;
    private Double pressure;
    private Double humidity;

    public Weather(WeatherDto dto) {
        city = dto.getName();
        temp = dto.getMain().getTemp();
        pressure = dto.getMain().getPressure();
        humidity = dto.getMain().getHumidity();
    }
}