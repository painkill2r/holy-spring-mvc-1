package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * HTTP message body에 직접 데이터를 담아서 요청이 온 경우를 처리하는 컨트롤러
 * 1. HTTP API에서 주로 사용, JSON, XML, TEXT
 * 2. 데이터 형식은 주로 JSON 사용
 * 3. POST, PUT, PATCH 메소드
 */
@Slf4j
@Controller
public class RequestBodyStringController {

    /**
     * HTTP 요청 파라미터와 다르게, HTTP 메시지 바디를 통해
     * 데이터가 직접 넘어오는 경우는 @RequestParam, @ModelAttribute를 사용할 수 없음.
     * <p>
     * HTTP 메시지 바디로 넘어온 데이터 읽는 방법 : HttpServletRequest.getInputStream() 이용
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}", messageBody);

        response.getWriter().write("ok");
    }

    /**
     * HTTP 메시지 바디로 넘어온 데이터 읽는 방법 : InputStream 이용
     * InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
     * OutputStream(Writer): HTTP 응답 메시지 바디에 직접 결과를 출력
     *
     * @param inputStream
     * @param responseWiter
     * @throws IOException
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWiter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody = {}", messageBody);

        responseWiter.write("ok");
    }

    /**
     * HTTP 메시지 바디로 넘어온 데이터 읽는 방법 : HttpEntity<T> 사용
     * HttpEntity: HTTP Header, Body 정보를 편리하게 조회
     * - 메시지 바디 정보를 직접 조회
     * - 요청 파라미터(GET, POST(x-www-form-urlencoded))를 조회하는 기능과 관계 없음.(@RequestParam X, @ModelAttribute X)
     * - HttpEntity는 응답에도 사용 가능
     * > 메시지 바디 정보 직접 반환
     * > 헤더 정보 포함 가능
     * > View 조회 X
     *
     * @param httpEntity
     * @throws IOException
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();

        log.info("messageBody = {}", messageBody);

        return new HttpEntity<>("ok");
    }

    /**
     * HTTP 메시지 바디로 넘어온 데이터 읽는 방법 : HttpEntity를 상속받은 RequestEntity<T> 사용(HTTP Method, URL 정보 추가 사용 가능)
     * 응답으로는 ResponseEntity<T> 사용(HTTP 상태코드 설정 가능)
     *
     * @param httpEntity
     * @return
     * @throws IOException
     */
    @PostMapping("/request-body-string-v3-2")
    public HttpEntity<String> requestBodyStringV32(RequestEntity<String> httpEntity) throws IOException {
        String messageBody = httpEntity.getBody();

        log.info("messageBody = {}", messageBody);

        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }

    /**
     * HTTP 메시지 바디로 넘어온 데이터 읽는 방법 : @RequestBody 사용(참고로 헤더 정보가 필요하다면 HttpEntity를 사용하거나 @RequestHeader를 사용하면 됨.)
     * 응답에는 @ResponseBody 사용
     * 위와 같이 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 @RequestParam, @ModelAttribute와는 전혀 관계가 없다.
     *
     * @param messageBody
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody, @RequestHeader MultiValueMap<String, String> headerMap) throws IOException {
        log.info("headers = {}", headerMap);
        log.info("messageBody = {}", messageBody);

        return "ok";
    }
}
