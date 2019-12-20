package rs.raf.sk.Subscriber.domain.dto;

import lombok.Data;
import rs.raf.sk.Subscriber.domain.City;
import rs.raf.sk.Subscriber.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserResDto {
    private String username;

    private String email;

    private List<String> cities;

    public UserResDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.cities = new ArrayList<>();
        //this.cities = user.getCities().stream().map(City::getName).collect(Collectors.toList());
    }
}
