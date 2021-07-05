package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * Content-Type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * HTTP message body로 전달된 JSON을 처리하는 방법 : HttpServletRequest, ObjectMapper 사용
     * 응답에는 HttpServletResponse 사용
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messagBody = {}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    /**
     * HTTP message body로 전달된 JSON을 처리하는 방법 : @RequestBody, ObjectMapper 사용
     * 응답에는 @ResponseBody 사용
     *
     * @param messageBody
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
        log.info("messagBody = {}", messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * HTTP message body로 전달된 JSON을 처리하는 방법 : @RequestBody 객체 파라미터 사용
     * - @RequestBody에 직접 만든 객체를 지정할 수 있음.
     * - HttpEntity, @RequestBody를 사용하면 'HTTP 메시지 컨버터'가 HTTP 메시지 바디의 내용을 우리가 원하는 문자나 객체 등으로 변환해 줌.
     * - 단, @RequestBody를 사용하는 경우 생략 불가(생략하면 @ModelAttribute가 적용되어 버림)
     * - HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter(Content-Type: application/json) 동작
     * <p>
     * 응답에는 @ResponseBody 사용
     *
     * @param helloData
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * HTTP message body로 전달된 JSON을 처리하는 방법 : HttpEntity<T> 사용
     *
     * @param httpEntity
     * @return
     */
    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData helloData = httpEntity.getBody();
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * HTTP message body로 전달된 JSON을 처리하는 방법 : @RequestBody 사용
     * 응답으로 @ResponseBody 사용
     * - 응답의 경우에도 @ResponseBody를 사용하면 해당 객체를 HTTP 메시지 바디에 직접 넣어줄 수 있다.
     * - 물론 이 경우에도 HttpEntity를 사용해도 된다.
     * - HttpMessageConverter -> MappingJackson2HttpMessageConverter 적용(Accept: application/json)
     *
     * @param helloData
     * @return
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData helloData) {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return helloData;
    }
}
