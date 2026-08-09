package jp.ponkichi.bbgreen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/api/healthCheck/freemessage/{message}")
  @SecurityRequirement(name = "")
  public Message freeMessage(@PathVariable String message) {
    return new Message(message);
  }


  // ToDo:整数のリストを受け取って、その合計を返す
  @GetMapping("/api/healthCheck/sum")
  public int sum(@RequestParam int a, @RequestParam int b) {
    return a + b;
  }

  // ToDo: DBへの接続を確認できる機能を追加する
}
