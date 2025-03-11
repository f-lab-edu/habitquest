package com.habitquest.service;

import com.habitquest.common.ErrorType;
import com.habitquest.exception.HabitQuestException;
import com.habitquest.service.dto.TokenResponseDTO;
import com.habitquest.common.Provider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

// SSO Provider를 세팅해주는 클래스
@Service
public class TakeSSOProvider {
  private final Map<Provider, SSOAuth> ssoAuth = new HashMap<>();

  /*
   Spring에서는 인터페이스를 구현한 모든 Bean을 자동으로 주입해줌
   즉 인터페이스 타입의 List<T>를 생성자 인자로 받을 경우, Spring 컨테이너가 해당 인터페이스를 구현한 모든 bean을 자동으로 찾아서 list에 넣어주게됨
   일반적인 new 키워드로 객체를 생성하는 경우에는 적용되지 않음 */
  public TakeSSOProvider(List<SSOAuth> ssoServices) {
    for (SSOAuth service : ssoServices) {
      if (service instanceof GoogleAuthService) {
        ssoAuth.put(Provider.GOOGLE, service);
      }
    }
  }
  public String getAuthUrl(Provider provider) {
    return getSSOAuth(provider).getAuthUrl();
  }

  public TokenResponseDTO ssoLogin(Provider provider, String code) {
    return getSSOAuth(provider).ssoLogin(code);
  }

  // provider 세팅 메소드
  private SSOAuth getSSOAuth(Provider provider) {
    SSOAuth sso = ssoAuth.get(provider);
    if (provider == null) {
      throw new HabitQuestException(ErrorType.INVALID_LOGIN);
    }
    return sso;
  }
}
