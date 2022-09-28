package org.lbw.story.user.api;

import org.lbw.story.common.Result;
import org.lbw.story.user.dao.ShopDao;
import org.lbw.story.user.model.Shop;
import org.lbw.story.user.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Transactional //事务 该注解导致本类的每一个方法内部对数据库的访问是一个整体
@RestController //该注解说明本类是一个提供api接口的类（控制器）
public class TestApi {

    @Autowired //该注解会导框架自动将ShopDao对象属性赋值
    private ShopDao shopDao;

    @GetMapping("/hello") //配置了一个 地址为"/hello"的api接口
    public String sayHello(){
        return "大家好！";
    }

    @GetMapping("/one-stu")
    public Student getStudent(){
        return new Student("001","学生1","男",4.5);
    }

    @GetMapping("/shop")
    public Result<List<Shop>> shopList(){
        List<Shop> list =  shopDao.findShopList();
        return Result.OK(list);
    }
}
