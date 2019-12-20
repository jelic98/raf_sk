package rs.raf.sk.Subscriber.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RegisterDto {

    @NotNull
    private String username;

    @Email
    private String email;

    @NotNull
    private String password;

}
