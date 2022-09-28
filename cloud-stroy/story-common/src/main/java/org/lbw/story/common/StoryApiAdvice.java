package org.lbw.story.common;


import lombok.extern.slf4j.Slf4j;
import org.lbw.story.common.exception.BusinessException;
import org.lbw.story.common.exception.NotAuthenticationException;
import org.lbw.story.common.exception.SysException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice //本注解表示，本类专门拦截注解为@RestController对象中的API接口方法
public class StoryApiAdvice {

    @ExceptionHandler(SysException.class) //本注解参数为SysException.class，表示该方法专门处理SysException异常
    public Result handleSysException(SysException e){
        log.error(e.getMessage(),e);
        return Result.err(Result.CODE_ERR_SYS,"错误代码："+Result.CODE_ERR_SYS);
    }

    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e){
        return Result.err(Result.CODE_ERR_BUSINESS,e.getMessage());
    }

    @ExceptionHandler(NotAuthenticationException.class)
    public Result handleNotAuthenticationException(NotAuthenticationException e){
        return Result.err(Result.CODE_ERR_UNLOGIN,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        log.error(e.getMessage(),e);
        return Result.err(Result.CODE_ERR_SYS,"系统出错了");
    }
}

