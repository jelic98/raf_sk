package rs.raf.sk.service.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import rs.raf.sk.service.domain.dto.ChannelDto;

import java.util.List;

@FeignClient(name = "remoteService", url = "http://localhost:2227")
public interface RemoteService {

    @GetMapping("/channel/local/findAll")
    List<ChannelDto> findChannels();

}
