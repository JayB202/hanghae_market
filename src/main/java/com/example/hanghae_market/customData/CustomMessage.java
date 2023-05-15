package com.example.hanghae_market.customData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomMessage {
    SUEEST_TO_GET_CHAT_ROOM_LIST(HttpStatus.OK, "채팅 목록 불러오기 성공!"),
    CHAT_ROOM_EXIT_SUCCESS(HttpStatus.OK, "채팅방에서 나오기 성공!"),
    CHAT_HISTORY_SUCCESS(HttpStatus.OK, "메세지 불러오기 성공!");
    private final HttpStatus httpStatus;
    private final String customMessage;
}
