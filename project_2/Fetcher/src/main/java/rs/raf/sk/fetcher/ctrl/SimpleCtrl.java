package rs.raf.sk.service.ctrl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.raf.sk.service.domain.dto.ChannelDto;
import rs.raf.sk.service.service.ChannelService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SimpleCtrl {

    private final ChannelService channelService;

    @GetMapping("/hello")
    public String hello(String name) {
        return "Hello " + name + "!";
    }

    @GetMapping("/fetchChannels")
    public List<ChannelDto> fetchChannels() { return channelService.fetchAll(); }

}
