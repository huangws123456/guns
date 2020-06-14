package cn.stylefeng.guns.sys.modular.account.entity;

import com.baomidou.mybatisplus.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author huangwuseng
 * @since 2020-06-08
 */
@TableName("account_info")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private String id;

    /**
     * 昵称
     */
    @TableField("user_name")
    @NotBlank
    private String userName;

    /**
     * 昵称
     */
    @TableField("user_id")
    @NotBlank
    private String userId;

    /**
     * 手机号
     */
    @TableField("phone")
    @NotBlank
    private String phone;


    /**
     * 状态
     */
    @TableField("status")
    private String status;


    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
}
