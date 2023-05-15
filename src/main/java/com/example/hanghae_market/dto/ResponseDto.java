package com.example.hanghae_market.dto;


import com.example.hanghae_market.customData.CustomMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;


@Getter
@Builder
@AllArgsConstructor
public class ResponseDto {
    private String message;
    private boolean status;
    private Object tData;


    public static ResponseEntity<ResponseDto> toResponseEntity(CustomMessage customMessage, Object data) {
        return ResponseEntity
                .status(customMessage.getHttpStatus())
                .body(ResponseDto.builder()
                        .status(!customMessage.getHttpStatus().isError())
                        .message(customMessage.getCustomMessage())
                        .tData(data)
                        .build()
                );
    }
    public static ResponseEntity<ResponseDto> toResponseEntity(CustomMessage customMessage) {
        return ResponseEntity
                .status(customMessage.getHttpStatus())
                .body(ResponseDto.builder()
                        .status(!customMessage.getHttpStatus().isError())
                        .message(customMessage.getCustomMessage())
                        .tData(customMessage)
                        .build()
                );
    }

//    public static ResponseEntity<ResponseDto> getMessages

}


