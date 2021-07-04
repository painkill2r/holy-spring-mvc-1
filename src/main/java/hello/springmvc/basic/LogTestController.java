package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 일반적으로 @Controller는 반환 값이 String이면 View 이름으로 인식된다.
 * 그래서 View를 찾고 View가 렌더링 된다.
 * 하지만 @RestController는 반환 값으로 View를 찾는 것이 아니라, HTTP 메시지 바디에 바로 입력한다.
 */
@Slf4j
@RestController
public class LogTestController {

    //@Slf4j 어노테이션을 사용하면 직접 선언하지 않아도 됨.
    //private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/log-test")
    public String logTest() {
        String name = "spring";

        //System.out.println("name = " + name);
        //로그 레벨 설정에 따라 출력 됨.
        //log.trace("[trace] name = " + name); // 연산이 발생하므로 권장하지 않음.
        log.trace("[trace] name = {}", name);
        log.debug("[debug] name = {}", name);
        log.info("[info] name = {}", name);
        log.warn("[warn] name = {}", name);
        log.error("[error] name = {}", name);

        return "ok";
    }
}
