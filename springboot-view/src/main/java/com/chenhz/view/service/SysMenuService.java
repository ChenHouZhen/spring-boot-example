package com.chenhz.view.service;

import com.chenhz.view.domain.SysMenuEntity;
import com.baomidou.mybatisplus.service.IService;
import com.chenhz.view.entity.Menus;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author 
 * @since 2019-04-30
 */
public interface SysMenuService extends IService<SysMenuEntity> {


    Menus getMenus();

}
