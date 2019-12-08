package rs.raf.sk.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.raf.sk.service.domain.dto.ChannelDto;
import rs.raf.sk.service.service.ChannelService;
import rs.raf.sk.service.service.feign.RemoteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final RemoteService remoteService;

    @Override
    public List<ChannelDto> fetchAll() {
        return remoteService.findChannels();
    }
}
