package rs.raf.sk.Subscriber.service;

import rs.raf.sk.Subscriber.domain.dto.LoginDto;
import rs.raf.sk.Subscriber.domain.dto.UserReqDto;
import rs.raf.sk.Subscriber.domain.dto.UserResDto;

public interface AuthService {

    UserResDto login(LoginDto loginDto);
}
