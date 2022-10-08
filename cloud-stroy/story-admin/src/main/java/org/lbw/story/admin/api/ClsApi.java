package org.lbw.story.admin.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.lbw.story.admin.dao.ClsDao;
import org.lbw.story.admin.dto.ClsDto;
import org.lbw.story.admin.model.Cls;
import org.lbw.story.common.Result;

import java.util.List;

@Slf4j
@Transactional
@RestController
@RequestMapping("/admin/cls")
public class ClsApi {

    @Autowired
    private ClsDao ClsDao;

    @GetMapping("")
    public Result<List<Cls>> ClsList(){
        return Result.OK(ClsDao.findClsList());
    }

    @PostMapping("")
    public Result ClsDoAdd(@RequestBody ClsDto ClsDto){
        ClsDao.insertCls(ClsDto);
        return Result.OK();
    }

    @PutMapping("")
    public Result ClsDoUpd(@RequestBody ClsDto ClsDto){
        ClsDao.updateCls(ClsDto);
        return Result.OK();
    }

    @DeleteMapping("/{cls_id}")
    public Result ClsDoDel(@PathVariable Integer cls_id){
        ClsDao.deleteCls(cls_id);
        return Result.OK();
    }

}
