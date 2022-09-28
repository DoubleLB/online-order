package org.lbw.story.user.api;

import lombok.extern.slf4j.Slf4j;
import org.lbw.story.common.StoryApiAdvice;
import org.lbw.story.common.exception.BusinessException;
import org.lbw.story.common.exception.SysException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.lbw.story.common.Result;
import org.lbw.story.user.dao.LoginDao;
import org.lbw.story.user.dto.LoginDto;
import org.lbw.story.user.model.User;

@Slf4j //该注解会导致编译时自动创建一个日志输出对象
@Transactional
@RestController
@RequestMapping("/login") //本API接口类统一的接口地址前缀
public class LoginApi extends StoryApiAdvice {

    @Autowired
    private LoginDao loginDao;//用于登陆的访问数据库的对象

    @PostMapping("")
    public Result doLogin(@RequestBody LoginDto loginDto){// 表示参数的格式是请求体（json格式的字符串）

        log.debug("收到参数：u_id={},u_pwd={}",loginDto.getU_id(),loginDto.getU_pwd());

        //根据传来的账号密码，从数据库中查询一条符合条件的用户数据，并封装到User对象中返回
        User user = null;
        try {
            user = loginDao.findUserByIdAndPwd(loginDto);
        } catch (Exception e) {
//            log.error("查询数据库错误!",e);
            throw new SysException("系统错误",e);
        }

        if(user == null){
            //账号或密码不正确
//            return Result.err(Result.CODE_ERR_BUSINESS,"错误");
            throw new BusinessException("账号密码不正确");
        }
        else {
            //账号密码正确
            return Result.OK();

        }
    }
}
