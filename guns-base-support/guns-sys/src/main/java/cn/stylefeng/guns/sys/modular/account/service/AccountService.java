package cn.stylefeng.guns.sys.modular.account.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.modular.account.entity.Account;
import cn.stylefeng.guns.sys.modular.account.mapper.AccountMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author huangwuseng
 * @since 2018-12-07
 */
@Service
public class AccountService extends ServiceImpl<AccountMapper, Account> {

    @Resource
    private AccountMapper AccountMapper;

    public Page<Map<String, Object>> list(String condition, String id) {
        Page page = LayuiPageFactory.defaultPage();
        return this.baseMapper.list(page, condition, id);
    }

    public void deleteAccount(String id) {
        Account account = AccountMapper.selectById(id);
        this.removeById(id);
    }
    public boolean removeById(Serializable id) {
        return SqlHelper.retBool(this.baseMapper.deleteById(id));
    }

    public void addAccount(Account account) {
        if (ToolUtil.isOneEmpty(account, account.getPhone(), account.getUserId(), account.getUserName())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        account.setStatus("0");
        this.save(account);
    }

    public void editAccount(Account account) {

        if (ToolUtil.isOneEmpty(account, account.getPhone(), account.getUserId(), account.getUserName())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        this.updateById(account);
    }
}
