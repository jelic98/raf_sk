package rs.raf.sk.Subscriber.domain.dto;

import lombok.Data;
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
    private WeatherDto message;

    public MessageDto(MessageDto dto){
        this.recipient = dto.getRecipient();
        this.subject = dto.getSubject();
        this.message = dto.getMessage();
    }
}
