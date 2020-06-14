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
package cn.stylefeng.guns.sys.modular.account.controller;


import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.log.BussinessLog;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.sys.core.constant.dictmap.DeptDict;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.core.log.LogObjectHolder;
import cn.stylefeng.guns.sys.modular.account.entity.Account;
import cn.stylefeng.guns.sys.modular.account.service.AccountService;
import cn.stylefeng.guns.sys.modular.system.entity.Role;
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
 * 设备控制器
 *
 * @author huangwuseng
 * @Date 2020年6月8日20:27:22
 */
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController {

    private String PREFIX = "/modular/account/";

    @Autowired
    private  AccountService accountService ;

    /**
     * 跳转到设备管理首页
     *
     ** @author huangwuseng
     *  * @Date 2020年6月8日20:27:22
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "account.html";
    }
    /**
     * 获取所有设备列表
     *
     * @author huangwuseng
     * @Date 020年6月8日20:27:22
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(value = "condition", required = false) String condition,
                       @RequestParam(value = "userName", required = false) String userName) {
        Page<Map<String, Object>> list = this.accountService.list(condition, userName);
        return LayuiPageFactory.createPageInfo(list);
    }
    /**
     * 跳转到添加设备
     *
     * @author huangwuseng
     * @Date 2020年6月8日20:27:22
     */
    @RequestMapping("/account_add")
    public String accountAdd() {
        return PREFIX + "account_add.html";
    }

    /**
     * 删除设备
     *
     * @author huangwuseng
     * @Date 2020年6月8日20:27:22
     */
    @BussinessLog(value = "删除设备", key = "id", dict = DeptDict.class)
    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseData delete(@RequestParam("id") String id) {
        accountService.deleteAccount(id);
        return SUCCESS_TIP;
    }
    /**
     * 新增设备
     *
     * @author huangwuseng
     * @Date 2020年6月8日20:27:22
     */
    @BussinessLog(value = "添加设备", key = "simpleName", dict = DeptDict.class)
    @RequestMapping(value = "/add")
    @ResponseBody
    public ResponseData add(@Valid Account account) {
        this.accountService.addAccount(account);
        return SUCCESS_TIP;
    }
    /**
     * 跳转到修改角色
     *
     * @author fengshuonan
     * @Date 2018/12/23 6:31 PM
     */
    @RequestMapping(value = "/account_edit")
    public String roleEdit(@RequestParam String id) {
        if (ToolUtil.isEmpty(id)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Account account = accountService.getById(id);
        LogObjectHolder.me().set(account);
        return PREFIX + "/account_edit.html";
    }
    /**
     * 设备详情
     *
     * @author huangwuseng
     * @Date 2020年6月8日20:27:22
     */
    @RequestMapping(value = "/detail/{id}")
    @ResponseBody
    public Object detail(@PathVariable("id") String id) {
        Account account = accountService.getById(id);
        return account;
    }

    /**
     * 修改设备
     *
     * @author huangwuseng
     * @Date 2020年6月8日20:27:22
     */
    @BussinessLog(value = "修改设备", key = "simpleName", dict = DeptDict.class)
    @RequestMapping(value = "/update")
    @ResponseBody
    public ResponseData update(@Valid Account account) {
        accountService.editAccount(account);
        return SUCCESS_TIP;
    }
}
