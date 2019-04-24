package b;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/anno")
public class DemoAnnoController {
	
	@RequestMapping(produces="text/plain;charset=UTF-8")
	public @ResponseBody String index(HttpServletRequest request) {
		return "url: "+request.getRequestURI()+" can access";
	}
	
	@RequestMapping(value = "/pathvar/{str}",produces = "text/plain;charset=UTF-8")
	public @ResponseBody String  demoPathVar(@PathVariable String str,HttpServletRequest request) {
		return "url: "+request.getRequestURI()+" can access , str: "+str;
	}
	
	@RequestMapping(value = "/requestParam", produces = "test/plain;charset=UTF-8")
	public @ResponseBody String passRequestParam(Long id,HttpServletRequest request) {
		return "url: "+request.getRequestURI()+" can access , id: "+ id;
	}
	
	@RequestMapping(value = "/obj", produces = "test/plain;charset=UTF-8")
	@ResponseBody
	public String passObj(DemoObj obj,HttpServletRequest request) {
		return "url"+request.getRequestURI()+"can access, obj id: "+ obj.getId()+"obj name:"+obj.getName();
	}
	
	@RequestMapping(value = {"/name1","/name2"},produces = "text/plain;charset=UTF-8")
	public @ResponseBody String remove(HttpServletRequest request) {
		return "url:"+ request.getRequestURI() + "can access";
	}
}
