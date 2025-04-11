package com.example.server;

import com.example.lombokUse.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class MyPostMethod {
    private static Cookie cookie;

    //模拟用户登陆成功获取到cookies，再访问其它接口获取到列表

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(HttpServletResponse response, @RequestParam String username,@RequestParam String password){
        if(username.equals("zhangsan") && password.equals("123456")){
            cookie=new Cookie("login","true");
            response.addCookie(cookie);
            return "login success";
        }
        return "用户名或密码错误";
    }
    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    public String getUserList(HttpServletRequest request,
                            @RequestBody User users){
        User user = new User();
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("login")
                    && cookie.getValue().equals("true")
                    && users.getUsername().equals("zhangsan")
                    && users.getPassword().equals("123456")){
                user.setName("lisi");
                user.setSex("male");
                user.setAge(18);
                return user.toString();
            }
        }
        return "参数不合法";
    }
}
