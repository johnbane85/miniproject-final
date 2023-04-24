package vttp.project.server.services;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

  String token;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

}
