利用Session来统计人数

@Controller
public class IndexController {
    
    @RequestMapping("/check")
    public ModelAndView check(HttpSession session) {
        Integer i = (Integer) session.getAttribute("count");
        if (i == null)
            i = 0;
        i++;
        session.setAttribute("count", i);
        ModelAndView mav = new ModelAndView("check");
        return mav;}}

check.jsp
	session中记录的访问次数：${count}

http://127.0.0.1:8080/springmvc/check   //每次访问都+1
 