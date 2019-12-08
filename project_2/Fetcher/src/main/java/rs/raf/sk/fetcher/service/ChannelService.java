package rs.raf.sk.fetcher.service;

import rs.raf.sk.fetcher.domain.dto.ChannelDto;
import java.util.List;

public interface ChannelService {

    List<ChannelDto> fetchAll();
}