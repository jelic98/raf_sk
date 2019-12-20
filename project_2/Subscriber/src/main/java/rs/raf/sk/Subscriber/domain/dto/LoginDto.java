package rs.raf.sk.Subscriber.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import rs.raf.sk.Subscriber.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LoginDto {

    @NotNull
    private String username;

    @NotNull
    private String password;

    public LoginDto(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

}
