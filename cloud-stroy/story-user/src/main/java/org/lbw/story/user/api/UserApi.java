package org.lbw.story.user.api;

import lombok.extern.slf4j.Slf4j;
import org.lbw.story.common.Result;
import org.lbw.story.common.StoryConstants;
import org.lbw.story.common.TokenUser;
import org.lbw.story.common.TokenUtils;
import org.lbw.story.user.dao.UserDao;
import org.lbw.story.user.dto.UserDto;
import org.lbw.story.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


@Slf4j
@Transactional
@RestController
@RequestMapping("/user/user")
public class UserApi {

    @Autowired
    private UserDao userDao;

    //获取用户信息
    @GetMapping("")
    public Result<User> userInfo(@RequestHeader(StoryConstants.HEADER_NAME_TOKEN) String token){
        TokenUser tokenUser = TokenUtils.getTokenUser(token);
        String id = tokenUser.getUserId();

        User user = userDao.getUserInfo(id);

        return Result.OK(user);
    }

    //更新用户信息
    /* 1 */
    @PostMapping("/edit")
    public Result userInfoEdit(UserDto userDto, MultipartFile file) throws IOException {

        String filename = userDto.getU_avatar();//有图片则仍沿用原文件名

        //没有则新建一个随机数作为文件名
        if(filename.equals("null")){//此处需要注意，从数据库端获取的null值在后端发送后为“null”字符串了，需要比较
            filename = UUID.randomUUID().toString().replace("-", "") + ".jpg";
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(StoryConstants.UPLOAD_USER_DIR + "/" + filename);

            byte[] b =new  byte[1024 * 500];
            int len = -1;
            while((len = inputStream.read(b)) != -1){
                outputStream.write(b,0,len);
            }

            outputStream.flush();

            userDto.setU_avatar(filename);
            userDao.UpdateUserInfo(userDto);

            return Result.OK();
        } finally {
            try {
                if (outputStream != null) outputStream.close();
            } catch (IOException e) {
                if (inputStream != null) inputStream.close();
            }
        }

    }
    /*2*/
    @PostMapping("/edit-no-img")
    public Result userInfoNoimgEdit(@RequestBody UserDto userDto){

        userDao.UpdateUserInfo(userDto);
        return Result.OK();
    }
}
