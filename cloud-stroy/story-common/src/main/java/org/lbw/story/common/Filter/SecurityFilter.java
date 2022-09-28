package org.lbw.story.common.Filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.lbw.story.common.Result;
import org.lbw.story.common.StoryConstants;
import org.lbw.story.common.TokenUser;
import org.lbw.story.common.TokenUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@WebFilter("/*")
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("-----------过滤器{}初始化了！-----------", this.getClass().getName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String path = ((HttpServletRequest) request).getServletPath();

        log.debug("path--------------------------->{}", path);

        //对于以public开头的地址，直接放行
        if (path.startsWith("/public")) {
            chain.doFilter(request, response);
            return;
        }

//        if (path.startsWith("/admin")){
//            chain.doFilter(request, response);
//            return;
//        }

//        if (!((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
//            String token = ((HttpServletRequest) request).getHeader(StoryConstants.HEADER_NAME_TOKEN);
//            if (!StringUtils.hasText(token)) {
//                token = request.getParameter(StoryConstants.HEADER_NAME_TOKEN);
//            }


        String token = ((HttpServletRequest) request).getHeader(StoryConstants.HEADER_NAME_TOKEN);

        try {
            TokenUser tokenUser = TokenUtils.getTokenUser(token);
            chain.doFilter(request, response);//放行
        } catch (Exception e) {
            //向客户端输出用户未登录的错误

            ObjectMapper objectMapper = new ObjectMapper();//工具对象，将java对象转换成json串

            Result err = Result.err(Result.CODE_ERR_UNLOGIN, e.getMessage());

            String jsonStr = objectMapper.writeValueAsString(err);

            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonStr);
            out.flush();
            out.close();
        }
    }
}
