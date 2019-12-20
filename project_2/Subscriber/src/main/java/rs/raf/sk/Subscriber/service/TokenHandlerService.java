package rs.raf.sk.Subscriber.service;

public interface TokenHandlerService {

    String getTokenByEmail(String email);

    String getEmailByToken(String token);

}
