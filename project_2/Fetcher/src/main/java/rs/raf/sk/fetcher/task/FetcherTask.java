package rs.raf.sk.fetcher.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rs.raf.sk.fetcher.ctrl.FetcherCtrl;
import rs.raf.sk.fetcher.domain.Weather;
import rs.raf.sk.fetcher.domain.dao.WeatherDao;

@Component
public class FetcherTask {

    @Autowired
    private WeatherDao weatherDao;

    @Autowired
    private FetcherCtrl fetcherCtrl;

    @Scheduled(fixedRate = 3600000)
    public void fetchAll() {
        for(Weather weather : weatherDao.findAll()) {
            weatherDao.save(Weather.builder()
                    .id(weather.getId())
                    .city(weather.getCity())
                    .temp(weather.getTemp())
                    .pressure(weather.getPressure())
                    .humidity(weather.getHumidity())
                    .build());
        }
    }
}