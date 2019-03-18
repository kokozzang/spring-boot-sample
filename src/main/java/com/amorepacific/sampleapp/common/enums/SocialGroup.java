package com.amorepacific.sampleapp.common.enums;

import lombok.Getter;
import lombok.Setter;

public enum SocialGroup {
  FACEBOOK("facebook","https://www.facebook.com/v3.2/dialog/oauth","https://graph.facebook.com/v3.2/oauth/access_token", "https://graph.facebook.com/v3.2/me"),
  KAKAO("kakao", "https://kauth.kakao.com/oauth/authorize", "https://kauth.kakao.com/oauth/token", "https://kapi.kakao.com/v2/user/me"),
  NAVER("naver","https://nid.naver.com/oauth2.0/authorize","https://nid.naver.com/oauth2.0/token", "https://openapi.naver.com/v1/nid/me");

  @Getter
  @Setter
  private String socialName;

  @Getter
  @Setter
  private String socialOAuthUrl;

  @Getter
  @Setter
  private String socialTokenUrl;

  @Getter
  @Setter
  private String socialUserUrl;

  SocialGroup(String socialName, String socialOAuthUrl, String socialTokenUrl, String socialUserUrl){
    this.socialName = socialName;
    this.socialOAuthUrl = socialOAuthUrl;
    this.socialTokenUrl = socialTokenUrl;
    this.socialUserUrl = socialUserUrl;
  }
}