package org.lbw.story.admin.api;

import lombok.extern.slf4j.Slf4j;
import org.lbw.story.admin.dao.MealDao;
import org.lbw.story.admin.dto.MealDto;
import org.lbw.story.admin.model.Meal;
import org.lbw.story.common.Result;
import org.lbw.story.common.StoryConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Transactional
@RestController
@RequestMapping("/admin/meal")
public class MealApi {

    @Autowired
    private MealDao mealDao;

    @GetMapping("")
    private Result<List<Meal>> mealList() {

        List<Meal> list = mealDao.findMealList();

        return Result.OK(list);
    }

//    @PostMapping("")
//    private Result<List<Meal>> mealDoAdd(@RequestBody MealDto mealDto){//主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；
//
//        mealDto.setInsert_time(new Date());
//        mealDao.insertMeal(mealDto);
//        return Result.OK();
//    }

    @PostMapping("")
    private Result mealDoAdd( MealDto mealDto, MultipartFile file) throws IOException {//主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；

        String filename = UUID.randomUUID().toString().replace("-", "") + ".jpg";
        log.debug("filename=======>{}", filename);


        InputStream in = null;
        OutputStream out = null;
        try {
            in = file.getInputStream();
            out = new FileOutputStream(StoryConstants.UPLOAD_DIR + "/" + filename);

            byte[] b = new byte[1024 * 500];
            int len = -1;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.flush();

            mealDto.setInsert_time(new Date());
            mealDto.setMeal_pic(filename);
            mealDao.insertMeal(mealDto);
            return Result.OK();

        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                if (in != null) in.close();
            }
        }


    }

    @PostMapping("/edit")
    private Result mealDoEdit(MealDto mealDto, MultipartFile file) throws IOException {//MultipartFile不能与@RequestBody同时使用
        String filename = mealDto.getMeal_pic();//仍沿用原文件名
        log.debug("filename=======>{}", filename);


        InputStream in = null;
        OutputStream out = null;
        try {
            in = file.getInputStream();
            out = new FileOutputStream(StoryConstants.UPLOAD_DIR + "/" + filename);

            byte[] b = new byte[1024 * 500];
            int len = -1;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.flush();

            mealDto.setMeal_pic(filename);
            mealDao.UpdateMeal(mealDto);
            return Result.OK();

        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                if (in != null) in.close();
            }
        }

    }

    @PostMapping("/edit-no-img")
    private Result mealDoEditNoImg(@RequestBody MealDto mealDto) throws IOException {

            mealDao.UpdateMeal(mealDto);
            return Result.OK();

    }

    @DeleteMapping("/{meal_id}")
    private Result mealDoDelete(@PathVariable Integer meal_id) throws IOException {

        mealDao.deleteMealById(meal_id);

        return Result.OK();

    }
}
