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


import cn.stylefeng.guns.base.i18n.context.TranslationContext;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.sys.core.constant.dictmap.DeptDict;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.modular.KSConfig.model.KSconfig;
import cn.stylefeng.guns.sys.modular.account.entity.Account;
import cn.stylefeng.guns.sys.modular.account.service.AccountService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Map;

/**
 * 快手直播控制器
 *
 * @author huangwuseng
 * @Date 2020年6月8日20:27:22
 */
@Controller
@RequestMapping("/KSconfig")
public class KSController extends BaseController {

    private String PREFIX = "/modular/KSconfig/";


    /**
     * 跳转到设备管理首页
     *
     ** @author huangwuseng
     *  * @Date 2020年6月8日20:27:22
     */
    @RequestMapping("/index")
    public String index() {
        System.out.println("=============================================================");
        return PREFIX + "ksIndex.html";
    }
    /**
     * 跳转到设备管理首页
     *
     ** @author huangwuseng
     *  * @Date 2020年6月8日20:27:22
     */
    @RequestMapping("/monitoring")
    public String monitoring() {
        return PREFIX + "monitoring.html";
    }

    /**
     * 提交
     *
     * @author huangwuseng
     * @Date 2020年6月8日20:27:22
     */
    @RequestMapping(value = "/submit")
    @ResponseBody
    public ResponseData submit(@Valid KSconfig ksconfig) {
        return ResponseData.success(ksconfig);
    }

}
