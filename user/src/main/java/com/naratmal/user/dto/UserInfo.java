package com.naratmal.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserInfo {
    String userName;
    String userEmail;
    String userLocation;
    String userNickname;
    //내 폰트 (리스트)
    List<Font> myFonts;
    //즐겨찾기 한 폰트(리스트)
    List<Font> likeFonts;
    //다운로드 한 폰트(리스트)
    List<Font> downloadFonts;
    //제작 대기중
    List<Font> waitingFonts;
}
