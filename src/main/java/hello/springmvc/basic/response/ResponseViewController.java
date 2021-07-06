package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * src/main/resources는 리소스를 보관하는 곳이고, 또 클래스패스의 시작 경로임.
 * 스프링 부트는 클래스패스의 다음 디렉토리에 있는 정적 리소스를 제공함.
 * - '/static', '/public', '/resources', '/META-INF/resources'
 * <p>
 * 뷰 템플릿
 * 뷰 템플릿을 거쳐서 HTML이 생성되고, View가 응답을 만들어서 전달한다.
 * 스프링 부트는 기본 뷰 템플릿 경로를 제공한다
 * 'src/main/resources/templates'
 */
@Controller
public class ResponseViewController {

    /**
     * HTTP 응답 - 뷰 템플릿
     * ModelAndView를 사용해서 View와 Model을 설정
     *
     * @return
     */
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mv = new ModelAndView("response/hello")
                .addObject("data", "hello!");

        return mv;
    }

    /**
     * HTTP 응답 - 뷰 템플릿
     *
     * @param model 뷰 템플릿에 전달할 데이터를 담는 Model 객체
     * @return
     */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");

        return "response/hello";
    }

    /**
     * @param model
     * @RequestMapping URL 매핑 정보와 뷰 템플릿 경로가 동일하면 return "{뷰 경로}" 하지 않아도 됨.
     * 조건 : 메소드 리턴 타입이 void이고, HttpServletResponse, OutputStream(Writer) 같은 HTTP 메시지 바디를 처리하는 파라미터가 없는 경우 사용 가능
     */
    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello!");
    }
}
