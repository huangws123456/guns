/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.sys.modular.KSConfig.controller;


import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.redis.RedisUtil;
import cn.stylefeng.guns.sys.modular.KSConfig.model.KSconfig;
import cn.stylefeng.guns.sys.netty.service.BaseNettyBean;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 快手直播控制器
 *
 * @author huangwuseng
 * @Date 2020年6月8日20:27:22
 */
@Controller
@RequestMapping("/KSconfig")
public class KSController extends BaseController {
    @Autowired
    private RedisUtil redisUtils;
    private String PREFIX = "/modular/KSconfig/";


    /**
     * 跳转到设备管理首页
     *
     ** @author huangwuseng
     *  * @Date 2020年6月8日20:27:22
     */
    @RequestMapping("/index")
    public String index() {
        return PREFIX + "KSIndex.html";
    }
    /**
     * 跳转到实时监控页面
     *
     ** @author huangwuseng
     *  * @Date 2020年6月8日20:27:22
     */
    @RequestMapping("/monitoring")
    public String monitoring() {
        return PREFIX + "monitoring.html";
    }
    /**
     * 跳转到实时监控页面
     *
     ** @author huangwuseng
     *  * @Date 2020年6月8日20:27:22
     */
    @RequestMapping("/list")
    @ResponseBody
    public Object monitoringList(@RequestParam(value = "condition", required = false) String condition) {
        Page<Object> list = new Page();
        Page page = LayuiPageFactory.defaultPage();
        List <Object> list1 = new ArrayList <Object>();
        Map <String ,BaseNettyBean> map0 = (Map<String, BaseNettyBean>) redisUtils.get("statusInfo");
        Map <String ,BaseNettyBean> map = new HashMap<>();
        if (map0 != null){
            if (condition!= null && !"".equals(condition)){
                for (String key:map0.keySet()){
                    if (key.contains(condition)){
                        map.put(key,map0.get(key));
                    }
                }
            }else{
                map =  map0;
            }
            if (map.size()==0){
                return LayuiPageFactory.createPageInfo(list);
            }
                List<BaseNettyBean> valuesList = new ArrayList<BaseNettyBean>(map.values());
            //Collections.sort(valuesList);
                Collections.sort(valuesList,new SortDate());
                int start =(int) (page.getSize()*(page.getCurrent()-1));
                int end = (int) (page.getSize()*page.getCurrent())>map.size()?map.size():(int) (page.getSize()*page.getCurrent());
                if (start>map.size()){
                    return LayuiPageFactory.createPageInfo(list);
                }
                for(int i = start; i<end; i++){
                    list1.add((Object) valuesList.get(i));
                }
            list.setRecords(list1);
            list.setTotal(map.size());
            list.setSize(map.size());
            list.setSize(page.getSize());
            list.setCurrent(page.getCurrent());
        }
        return LayuiPageFactory.createPageInfo(list);
    }

   class SortDate implements Comparator {
        public int compare(Object o1, Object o2) {
            BaseNettyBean s1 = (BaseNettyBean) o1;
            BaseNettyBean s2 = (BaseNettyBean) o2;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date dt1 = format.parse(s1.getDate());
                Date dt2 = format.parse(s2.getDate());
                if (dt1.getTime() < dt2.getTime()) {
                    return 1;
                } else if (dt1.getTime() >dt2.getTime()) {
                    return -1;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }
    /**
     * 提交
     *
     * @author huangwuseng
     * @Date 2020年6月8日20:27:22
     */
    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData submit(@Valid KSconfig ksconfig)
    {
      /* Map <String ,Object> map = (Map<String, Object>) redisUtils.get("statusInfo");
        if (map == null){
            map = new HashMap<String ,Object>();
        }
        for (int i=15;i<18;i++){
        BaseNettyBean baseNettyBean = new BaseNettyBean();
        baseNettyBean.setDeveice("设备"+i);
        baseNettyBean.setDes("连接成功");
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        baseNettyBean.setDate(dateformat.format(System.currentTimeMillis()));
        map.put("key"+i,baseNettyBean);
        }
        redisUtils.set("statusInfo",map);*/
        return ResponseData.success(ksconfig);
    }

}
