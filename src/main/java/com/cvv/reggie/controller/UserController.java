package com.cvv.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cvv.reggie.common.R;
import com.cvv.reggie.common.ValidateCodeUtils;
import com.cvv.reggie.entity.User;
import com.cvv.reggie.service.UserService;
import com.cvv.reggie.utils.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Random;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */


@Slf4j
@RestController
@RequestMapping("/user")

public class UserController {

    @Resource
    private UserService userService;
    /**
     * 发送验证码
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){

        if (StringUtils.isNotBlank(user.getEmail())){
            String checkCode = ValidateCodeUtils.generateValidateCode4String(6);
            String text = "您的登录验证码为：" + checkCode + "。有效时间为为5分钟。";
            String title = "瑞吉外卖验证";
            MailUtils.sendMail(user.getEmail(),text,"瑞吉外卖验证");

            session.setAttribute(user.getEmail(),checkCode);

            return R.success("验证码已发送");
        }
        return R.error("验证码发送失败");
    }

    @PostMapping("/login")
    public R<User> userlogin(@RequestBody Map data, HttpSession session){

        String email = data.get("email").toString();
        String code = data.get("code").toString();

        String sessionCode = (String) session.getAttribute(email);
        if (StringUtils.isNotBlank(sessionCode) && sessionCode.equals(code)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getEmail,email);

            User user = userService.getOne(queryWrapper);
            if (user == null){
                user = new User();
                user.setEmail(email);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }

        return R.error("登录失败");
    }

}
