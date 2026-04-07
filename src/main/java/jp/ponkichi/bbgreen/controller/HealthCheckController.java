package jp.ponkichi.bbgreen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jp.ponkichi.bbgreen.dto.element.Message;
import jp.ponkichi.bbgreen.service.HealthCheckService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

  private final HealthCheckService healthCheckService;

  @GetMapping("/api/healthCheck/hello")
  @SecurityRequirement(name = "")
  public Message hello() {
    return new Message("Hello 38252!");
  }

  // ToDo: パスパラメータとして与えられた文字列を使った Message を返す機能を追加する。

  // ToDo: DBへの接続を確認できる機能を追加する
}
