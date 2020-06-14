package cn.stylefeng.guns.sys.modular.account.mapper;

import cn.stylefeng.guns.base.pojo.node.LayuiTreeNode;
import cn.stylefeng.guns.base.pojo.node.TreeviewNode;
import cn.stylefeng.guns.base.pojo.node.ZTreeNode;
import cn.stylefeng.guns.sys.modular.account.entity.Account;
import cn.stylefeng.guns.sys.modular.system.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface AccountMapper extends BaseMapper<Account> {

    /**
     * 获取所有设备列表
     */
    Page<Map<String, Object>> list(@Param("page") Page page, @Param("condition") String condition, @Param("userName") String userName);
}
