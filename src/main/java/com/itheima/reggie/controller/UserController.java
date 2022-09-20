package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R<User> login(HttpServletRequest request, @RequestBody User user) {
        //1.将页面提交的password密码进行md5加密处理
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的用户名phone查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, user.getPhone());
        User us = userService.getOne(queryWrapper);

        //3.如果没有查询到则返回登录失败结果
        if (us == null) {
            return R.error("未查到该账号，请注册后再登录");
        }

        //4.密码比对，如果不一致则返回登录失败结果
        if (!us.getPassword().equals(password)) {
            return R.error("密码错误，请检查");
        }

        //5.查看用户状态，如果返回已禁用状态，则返回用户已禁用结果
        if (us.getStatus() == 0) {//禁用
            return R.error("该账号已禁用");
        }

        //6.登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("user", user.getId());
        return R.success(us, "登录成功");
    }

    /**
     * 用户注册
     *
     * @param request
     * @param user
     * @return
     */
    @PostMapping("/regist")
    public R<String> regist(HttpServletRequest request, @RequestBody User user) {
        //1.将页面提交的password密码进行md5加密处理
        String password = user.getPassword();
        if (password.length() < 6) {
            return R.error("密码不能少于六位", 0);
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的用户名phone查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, user.getPhone());
        User us = userService.getOne(queryWrapper);

        //3.如果没有查询到则注册
        if (us == null) {
            user.setPassword(password);
            userService.save(user);
            return R.success(null, "注册成功");
        }

        //已注册
        return R.error("账号已注册，请登录", 0);
    }

    /**
     * 用户退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中保存的用户id
        request.getSession().removeAttribute("employee");
        return R.success(null, "退出成功");
    }

    /**
     * 根据id查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            return R.success(user, "操作成功");
        }
        return R.error("没有查询到对应用户信息", 0);
    }
}
