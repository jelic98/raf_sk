package rs.raf.sk.mailer.dto;

import lombok.Data;

@Data
public class MailDto {

    private String recipient;
    private String subject;
    private String message;
}