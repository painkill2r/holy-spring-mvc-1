package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 일반적으로 @Controller는 반환 값이 String이면 View 이름으로 인식된다.
 * 그래서 View를 찾고 View가 렌더링 된다.
 * 하지만 @RestController는 반환 값으로 View를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다.
 */
@RestController
public class LogTestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/log-test")
    public String logTest() {
        String name = "spring";

        //System.out.println("name = " + name);
        //로그 레벨 설정에 따라 출력됨
        log.trace("[trace] name = {}", name);
        log.debug("[debug] name = {}", name);
        log.info("[info] name = {}", name);
        log.warn("[warn] name = {}", name);
        log.error("[error] name = {}", name);

        return "ok";
    }
}
