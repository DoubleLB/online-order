package org.lbw.story.pub.api;

import lombok.extern.slf4j.Slf4j;
import org.lbw.story.common.*;
import org.lbw.story.common.exception.BusinessException;
import org.lbw.story.pub.dao.PublicDao;
import org.lbw.story.pub.dto.AdminLoginDto;
import org.lbw.story.pub.dto.LoginDto;
import org.lbw.story.pub.dto.RegisterDto;
import org.lbw.story.pub.dto.ShopMealDto;
import org.lbw.story.pub.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Transactional
@RestController
@RequestMapping("/public")
public class PublicAPI {

    @Autowired
    private PublicDao publicDao;

    @GetMapping("/img/{imgname}")
    public void img(@PathVariable String imgname, OutputStream out) throws IOException {//@说明参数来源于路径中

        String path = StoryConstants.UPLOAD_DIR + "/" + imgname;
        InputStream in = new FileInputStream(path);
        byte[] b = new byte[1024 * 500];
        int len = -1;
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }
        out.flush();
        out.close();
        in.close();
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {

        String filename = UUID.randomUUID().toString().replace("-", "")+".jpg";
        log.debug("filename=======>{}", filename);


        InputStream in = null;
        OutputStream out = null;
        out = null;
        try {
            in = file.getInputStream();
            out = new FileOutputStream(StoryConstants.UPLOAD_DIR + "/" + filename);

            byte[] b = new byte[1024 * 500];
            int len = -1;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.flush();
            return Result.OK("上传成功", filename);
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                if (in != null) in.close();
            }
        }

    }

    @GetMapping("/cls")
    public Result<List<Cls>> clsList() {
        List<Cls> list = publicDao.findClsList();
        return Result.OK(list);
    }

    @PostMapping("/admin/login")
    public Result<String> adminLogin(@RequestBody AdminLoginDto adminLoginDto){
        Admin admin = publicDao.findAdminByIdAndPwd(adminLoginDto);

        if(admin!=null){
            TokenUser tokenUser = TokenUser.creatAdmin(admin.getA_id(),admin.getA_name(),admin.getShop_id(),admin.getShop_name());

            String token = TokenUtils.loginSign(tokenUser);
            return Result.OK("登陆成功",token);
        }

        //登陆失败
        return Result.err(Result.CODE_ERR_BUSINESS,"登录失败");
    }

    @GetMapping("/login/verify-code")
    public void outVerificationCodeImage(String key,OutputStream out) throws IOException {
        VerificationCodeUtils.outImage(key , out);

    }

    //普通用户登录
    @PostMapping("/user/login")
    public Result<String> adminLogin(@RequestBody LoginDto loginDto){

        //从缓存中获取验证码
        String verifyCode = VerificationCodeUtils.getVerifyCode(loginDto.getKey());
        if(!StringUtils.hasText(verifyCode)){
            return Result.err(Result.CODE_ERR_BUSINESS,"验证码错误");
        }

        if(!verifyCode.equalsIgnoreCase(loginDto.getU_valid())){
            return Result.err(Result.CODE_ERR_BUSINESS,"验证码错误");
        }

        User user = publicDao.findUserByIdAndPwd(loginDto);

        if(user!=null){
            TokenUser tokenUser = TokenUser.creatUser(
                    user.getU_id(), StringUtils.hasText(user.getU_nickname()) ? user.getU_nickname(): user.getU_id()
            );

            String token = TokenUtils.loginSign(tokenUser);
            return Result.OK("登陆成功",token);
        }

        //登陆失败
        return Result.err(Result.CODE_ERR_BUSINESS,"登录失败");
    }

    @GetMapping("/is-admin")
    public Result<Map<String,Boolean>> isAdmin(@RequestHeader("Token") String token){
        Map<String,Boolean> map = new HashMap<>();
        try {
            TokenUser tokenUser = TokenUtils.getTokenUser(token);
            map.put("isAdmin",tokenUser.isAdmin());
            return Result.OK(map);
        } catch (Exception e) {
            map.put("isAdmin",false);
            return Result.OK(map);
        }

    }

    //是否为普通消费者
    @GetMapping("/is-user")
    public Result<Map<String,Boolean>> isUser(@RequestHeader("Token") String token){
        Map<String,Boolean> map = new HashMap<>();
        try {
            TokenUser tokenUser = TokenUtils.getTokenUser(token);
            map.put("isUser",tokenUser.isUser());
            return Result.OK(map);
        } catch (Exception e) {
            map.put("isUser",false);
            return Result.OK(map);
        }

    }

    //测试1 获取当前登录信息
    @GetMapping("/curr-user")
    public Result<TokenUser> getTokenUser(@RequestHeader("Token") String token){
        TokenUser tokenUser = TokenUtils.getTokenUser(token);
        return Result.OK(tokenUser);
    }

    //测试2 验证令牌
    @GetMapping("/verify-token")
    public Result verifyToken(@RequestHeader("Token") String token){
        TokenUtils.verify(token);
        return Result.OK();
    }

    //获取门店的清单
    @GetMapping("/shop-list")
    public Result<List<Shop>> shopList(){
        List<Shop> shopList = publicDao.findAllShopList();
        return Result.OK(shopList);
    }
    
    //根据条件查询门店餐品
    @GetMapping("/shop-meal")
    public Result<List<ShopMeal>> shopMealList(ShopMealDto shopMealDto){

        //查询门店全信息
        List<ShopMeal> shopMealList = publicDao.findShopMealWithFull(shopMealDto);
        return Result.OK(shopMealList);
    }

    //注册api接口
    @PostMapping("/register")
    public Result addUser(@RequestBody RegisterDto dto){
        User user = publicDao.findUserById(dto.getU_id());
        if(user != null ){
            throw new BusinessException("账号已存在！");
        }
        publicDao.insertUser(dto);
        return Result.OK();
    }
}
