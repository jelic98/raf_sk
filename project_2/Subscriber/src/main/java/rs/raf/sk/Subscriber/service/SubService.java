package rs.raf.sk.Subscriber.service;

import rs.raf.sk.Subscriber.domain.dto.UserReqDto;

import java.util.List;

public interface SubService {
    List<String> subscribe(String city, UserReqDto dto);

    List<String> unsubscribe(String city, UserReqDto dto);

    List<String> showUsers(String city);
}
