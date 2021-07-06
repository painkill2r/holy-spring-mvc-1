package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
@ResponseBody
//@RestController
public class ResponseBodyController {

    /**
     * HTTP 응답 - 문자열 데이터
     * HttpServletResponse 사용
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    /**
     * HTTP 응답 - 문자열 데이터
     * ResponseEntity 사용
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() throws IOException {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    /**
     * HTTP 응답 - 문자열 데이터
     * 어노테이션 사용 : @ResponseBody
     *
     * @return
     * @throws IOException
     */
    //@ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() throws IOException {
        return "ok";
    }

    /**
     * HTTP 응답 - JSON 데이터
     * ResponseEntity를 사용하면 데이터와 함께 HTTP 상태코드를 전달할 수 있음.
     *
     * @return
     * @throws IOException
     */
    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() throws IOException {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);


        return new ResponseEntity<>(helloData, HttpStatus.OK);
    }

    /**
     * HTTP 응답 - JSON 데이터
     * <p>
     * ResponseEntity를 사용하지 않는 경우 HTTP 상태코드를 전달 할 수 없다.
     * 이를 해결하기 위해 @ResponseStatus 어노테이션을 사용한다.
     * 단점: 상태 코드를 동적으로 변경할 수 없기 때문에 프로그램 조건에 따라서 동적으로 변경하려면 ResponseEntity를 사용하면 됨.
     *
     * @return
     * @throws IOException
     */
    @ResponseStatus(HttpStatus.OK)
    //@ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() throws IOException {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return helloData;
    }
}
