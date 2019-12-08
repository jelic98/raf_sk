package rs.raf.sk.fetcher.ctrl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.sk.fetcher.domain.dto.ChannelDto;
import rs.raf.sk.fetcher.service.ChannelService;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SimpleCtrl {

    private ChannelService channelService;

    @GetMapping("/hello")
    public String hello(String name) {
        return "Hello " + name + "!";
    }

    @GetMapping("/fetchChannels")
    public List<ChannelDto> fetchChannels() {
        return channelService.fetchAll();
    }
}