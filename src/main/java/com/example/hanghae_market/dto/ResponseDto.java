
package com.example.hanghae_market.dto;


import com.example.hanghae_market.customData.CustomMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Getter
@Builder
@AllArgsConstructor(staticName = "set")
public class ResponseDto<T> {
    private String message;
    private boolean status;
    private HttpStatus httpStatus;
    private T data;


    public static ResponseEntity<ResponseDto> toResponseEntity(CustomMessage customMessage, Object data) {
        return ResponseEntity
                .status(customMessage.getHttpStatus())
                .body(ResponseDto.builder()
                        .status(!customMessage.getHttpStatus().isError())
                        .message(customMessage.getCustomMessage())
                        .data(data)
                        .build()
                );
    }
    public static ResponseEntity<ResponseDto> toResponseEntity(CustomMessage customMessage) {
        return ResponseEntity
                .status(customMessage.getHttpStatus())
                .body(ResponseDto.builder()
                        .status(!customMessage.getHttpStatus().isError())
                        .message(customMessage.getCustomMessage())
                        .data(customMessage)
                        .build()
                );
    }

    public static <T> ResponseDto<T> setSuccess(String message, T data){
        return ResponseDto.set(message, true, HttpStatus.OK, data);
    }
    public static <T> ResponseDto<T> setBadRequest(String message){
        return ResponseDto.set(message, true, HttpStatus.OK, null);
    }
    public static <T> ResponseDto<T> setBadRequest(String message, T data){
        return ResponseDto.set(message, true, HttpStatus.OK, data);
    }

    public static <T> ResponseDto<T> setSuccess(String message){
        return ResponseDto.set(message, true, HttpStatus.OK, null);
    }
//    public static ResponseEntity<ResponseDto> getMessages

}

