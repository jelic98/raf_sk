package rs.raf.sk.Subscriber.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class MessageDto {

    @Email
    private String recipient;

    @NotNull
    private String subject;

    @NotNull
    private String message;

    public MessageDto(String r, String s, String m){
        this.recipient = r;
        this.subject = s;
        this.message = m;
    }

}
