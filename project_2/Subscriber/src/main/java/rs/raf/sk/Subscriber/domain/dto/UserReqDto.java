package rs.raf.sk.Subscriber.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import rs.raf.sk.Subscriber.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserReqDto {

    @NotNull
    private String username;

    @Email(message = "Email je obavezan!")
    private String email;

    @Length(min = 5, max = 25, message = "Izmedju 5 i 25")
    private String password;

    public UserReqDto(User user){
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
