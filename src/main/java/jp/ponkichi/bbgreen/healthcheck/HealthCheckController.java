package jp.ponkichi.bbgreen.healthcheck;

import jp.ponkichi.bbgreen.healthcheck.dto.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/healthCheck/hello")
    public Message hello() {
        return new Message("Hello 38252!");
    }

    //To do: パスパラメータとして与えられた名前を使った文字列を返す機能を追加する。その際、インスタンスの生成は HealthCheckService クラスで行う

    //To do: DBへの接続を確認する機能を追加する
}