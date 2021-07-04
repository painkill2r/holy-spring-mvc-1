package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MappingController {

    /**
     * URL 매핑을 다중 설정이 가능함.(배열)
     * method 속성을 지정하지 않으면 HTTP 메소드와 무관하게 호출 됨.
     * 모두 허용 : GET, HEAD, POST, PUT, PATCH, DELETE
     *
     * @return
     */
    @RequestMapping({"/hello-basic", "/hello-go"})
    public String helloBasic() {
        log.info("helloBasic");

        return "ok";
    }

    /**
     * method 속성을 사용하면 특정 HTTP 메소드 요청만 허용.
     * GET, HEAD, POST, PUT, PATCH, DELETE
     *
     * @return
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mappingGetV1");

        return "ok";
    }

    /**
     * 편리한 축약 애노테이션
     *
     * @return
     * @GetMapping
     * @PostMapping
     * @PutMapping
     * @PatchMapping
     * @DeleteMapping
     */
    @GetMapping("/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mappingGetV2");

        return "ok";
    }

    /**
     * PathVariable 사용(/mapping/userA)
     * 변수명이 같으면 생략 가능
     *
     * @return
     * @PathVariable("userId") String userId -> @PathVariable String userId
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable String userId) {
        log.info("mappingPath userId = {}", userId);

        return "ok";
    }

    /**
     * PathVariable 사용 다중(/mapping/users/userA/orders/1)
     *
     * @param userId
     * @param orderId
     * @return
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {
        log.info("mappingPath userId = {}, orderId = {}", userId, orderId);

        return "ok";
    }

    /**
     * 특정 파라미터 추가 매핑
     * <p>
     * params="mode"
     * params="!mode"
     * params="mode=debug" -> 파라미터로 mode=debug가 있어야만 요청이 매핑됨.
     * params="mode!=debug"
     * params={"mode=debug", "data=good"}
     *
     * @return
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");

        return "ok";
    }

    /**
     * 특정 헤더 추가 매핑
     * <p>
     * headers = "mode"
     * headers = "!mode"
     * headers = "mode=debug"
     * headers = "mode!=debug"
     *
     * @return
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");

        return "ok";
    }

    /**
     * 미디어 타입 조건 매핑
     * HTTP 요청 Content-Type 헤더 기반 추가 매핑(Media Type)
     * <p>
     * consumes="application/json" -> Content-Type이 application/json인 경우 요청이 매핑됨.
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     *
     * @return
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsumes");

        return "ok";
    }

    /**
     * 미디어 타입 조건 매핑
     * HTTP 요청 Accept 헤더 기반 추가 매핑(Media Type)
     * <p>
     * produces="text/html" -> Accept 헤더가 text/html인 경우 요청이 매핑됨.
     * produces="!text/html"
     * produces="text/*"
     * produces="*\/*"
     * MediaType.TEXT_PLAIN_VALUE
     *
     * @return
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_PLAIN_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");

        return "ok";
    }
}
