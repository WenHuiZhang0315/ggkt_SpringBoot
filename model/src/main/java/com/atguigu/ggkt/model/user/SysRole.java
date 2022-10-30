package com.atguigu.ggkt.model.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.atguigu.ggkt.model.base.BaseEntity;
import lombok.Data;


@Data
@TableName("sys_role")
public class SysRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@TableField("role_name")
	private String roleName;

	@TableField("role_code")
	private String roleCode;

	@TableField("description")
	private String description;

}

