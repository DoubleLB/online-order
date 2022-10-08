package org.lbw.story.admin.api;

import lombok.extern.slf4j.Slf4j;
import org.lbw.story.admin.dao.AdminDao;
import org.lbw.story.admin.dto.AdminDto;
import org.lbw.story.common.Result;
import org.lbw.story.common.StoryConstants;
import org.lbw.story.common.TokenUser;
import org.lbw.story.common.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Transactional
@RestController
@RequestMapping("/admin/admin")
public class AdminApi {

    @Autowired
    private AdminDao adminDao;

    @PostMapping("/pwd")
    public Result updatePassword(@RequestHeader(StoryConstants.HEADER_NAME_TOKEN) String token
                                 ,@RequestBody String a_pwd){

        TokenUser tokenUser = TokenUtils.getTokenUser(token);
        String a_name = tokenUser.getUserId();

        adminDao.updatePwd(a_name,a_pwd);
        return Result.OK();
    }

    @GetMapping("")
    public Result<List> getAdminInfo(@RequestHeader(StoryConstants.HEADER_NAME_TOKEN) String token){

        TokenUser tokenUser = TokenUtils.getTokenUser(token);
        Integer shopId = tokenUser.getShopId();

        return Result.OK(adminDao.findAdmin(shopId));

    }

    @GetMapping("/shop")
    public Result<List> getShopList(){
        return Result.OK(adminDao.getShopList());
    }

    @PostMapping("")
    public Result AdminDoAdd(@RequestBody AdminDto adminDto){
        adminDao.insertAdmin(adminDto);
        return Result.OK();
    }

    @PutMapping("")
    public Result AdminDoUpd(@RequestBody AdminDto adminDto){
        adminDao.updateAdmin(adminDto);
        return Result.OK();
    }

    @DeleteMapping("/{a_id}")
    public Result AdminDoDel(@PathVariable String a_id){
        adminDao.deleteAdmin(a_id);
        return Result.OK();
    }

}
