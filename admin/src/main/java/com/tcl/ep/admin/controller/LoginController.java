package com.tcl.ep.admin.controller;

import com.tcl.ep.admin.utils.MD5Utils;
import com.tcl.ep.biz.service.UserService;
import com.tcl.ep.persistence.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by panmin on 16-11-23.
 */
@Controller
public class LoginController {

    @Resource
    private UserService userService;

    /**
     * 主界面
     *
     * @return 结果集
     */
    @RequestMapping("/admin/index")
    public String index() {

        return "/admin/index";
    }

    /**
     * 登录的展示页
     *
     * @return 'login'
     */
    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("errorInfo", "");
        return "login";
    }

    /**
     * 处理登录请求,其实这个更好的是一个ajax过程
     * MD5Signature
     * @param userName userName
     * @param password password
     * @param model model
     * @return index or login page
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(String userName, String password, Model model, HttpSession session) {
        String errorInfo = "";
        UserInfo user = null;
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            errorInfo = "用户名或密码未输入!";
        } else {
            user = userService.selectByUserName(userName);
            if (user == null) {
                errorInfo = "用户名不存在!";
            } else {
                try {
                    if (MD5Utils.validPassword(password, user.getPassword())) {
                        // 登录验证处理
                        // 1. 生成token
                        session.setAttribute("userInfo", user.getRealName());
                        return "redirect:/project/list";
                    } else {
                        errorInfo = "密码错误!";
                    }
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }

        model.addAttribute("errorInfo", errorInfo);
        return "login";
    }

    /**
     * 退出操作,移除当前session中的内容,并转到登录页
     *
     * @param request request
     * @return 返回页面
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("userInfo");
        return "redirect:/login";
    }
}
