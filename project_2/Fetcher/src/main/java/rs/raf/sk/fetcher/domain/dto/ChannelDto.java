package rs.raf.sk.fetcher.domain.dto;

import lombok.Data;
import java.util.List;

@Data
public class ChannelDto {

    private Long id;
    private String name;
    private String logoPath;
    private List<TypeDto> types;
}