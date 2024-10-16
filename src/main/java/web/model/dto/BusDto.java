package web.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BusDto {
    private int resNo;
    private String gameCode;
    private int memberId;
    private String resTime;
    private int reStatus;
    private int seat;
}
