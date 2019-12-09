package rs.raf.sk.fetcher.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rs.raf.sk.fetcher.ctrl.FetcherCtrl;
import rs.raf.sk.fetcher.domain.Weather;
import rs.raf.sk.fetcher.domain.dao.WeatherDao;
import rs.raf.sk.fetcher.domain.dto.WeatherDto;

@Component
public class FetcherTask {

    @Autowired
    private WeatherDao weatherDao;

    @Autowired
    private FetcherCtrl fetcherCtrl;

    @Scheduled(fixedRate = 3600000)
    public void fetchAll() {
        for(Weather current : weatherDao.findAll()) {
            String city = current.getCity();
            WeatherDto dto = fetcherCtrl.fetchByCity(city);
            Weather updated = new Weather(dto);
            updated.setId(current.getId());

            weatherDao.save(updated);
        }
    }
}