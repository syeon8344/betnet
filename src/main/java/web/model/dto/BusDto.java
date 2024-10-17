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
    private int pointlogid;
    private int reStatus;
    private int seat;
    private int memberId;
}
