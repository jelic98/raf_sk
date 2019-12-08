package rs.raf.sk.fetcher.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.raf.sk.fetcher.domain.dto.ChannelDto;
import rs.raf.sk.fetcher.service.ChannelService;
import rs.raf.sk.fetcher.service.feign.RemoteService;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private RemoteService remoteService;

    @Override
    public List<ChannelDto> fetchAll() {
        return remoteService.findChannels();
    }
}