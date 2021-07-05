package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    /**
     * 요청 파라미터 처리 : HttpServletRequest, HttpServletResponse 사용
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username = {}, age = {}", username, age);

        response.getWriter().write("ok");
    }

    /**
     * 요청 파라미터 처리 : @RequestParam 사용
     *
     * @param memberName
     * @param memberAge
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName, @RequestParam("age") int memberAge) {
        log.info("username = {}, age = {}", memberName, memberAge);

        return "ok";
    }

    /**
     * 요청 파라미터 처리 : @RequestParam 사용(요청 파라미터명과 변수명(String, int, Integer와 같은 단순 타입의 경우)이 같으면 @RequestParam 이름을 생략 가능)
     *
     * @param username
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username, @RequestParam int age) {
        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    /**
     * 요청 파라미터 처리 : 요청 파라미터명과 메소드 파라미터 변수명이 같으면 @RequestParam 생략 가능
     *
     * @param username
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    /**
     * 요청 파라미터 처리 : @RequestParam 사용, required 속성을 사용하면 필수 파라미터를 설정할 수 있음.
     *
     * @param username
     * @param age      int로 설정하면 null인 경우에 대해 에러(500 예외)가 발생하기 때문에 Integer로 설정
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username, @RequestParam(required = false) Integer age) {
        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    /**
     * 요청 파라미터 처리 : @RequestParam 사용, defaultValue 속성을 사용하면 기본 값을 설정할 수 있음.
     *
     * @param username
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                      @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username = {}, age = {}", username, age);

        return "ok";
    }

    /**
     * 모든 파라미터를 Map(key=value / ex) key=userId, value=id1), MultiValueMap(key=[value1, value2, ...] / ex) key=userIds, value=[id1, id2, ...])으로 조회할 수 있음.
     *
     * @param paramMap
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username = {}, age = {}", paramMap.get("username"), paramMap.get("age"));

        return "ok";
    }

    /**
     * @param helloData
     * @return
     * @ModelAttribute 동작 과정
     * 1. HelloData 객체 생성.
     * 2. 요청 파라미터 이름으로 HelloData 객체의 프로퍼티를 찾음.
     * 3. 해당 프로퍼티의 setter를 호출해서 파라미터 값을 입력(바인딩).
     * - 파라미터 이름이 username이면 setUsername() 메소드를 찾아 호출하면서 값을 입력함.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData);

        return "ok";
    }

    /**
     * @param helloData
     * @return
     * @ModelAttribute 는 생략 가능함.
     * (단, Argument  Resolver로 지정된 타입은 @ModelAttribute를 적용할 수 없음. ex) HttpServletRequest)
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());
        log.info("helloData = {}", helloData);

        return "ok";
    }
}
