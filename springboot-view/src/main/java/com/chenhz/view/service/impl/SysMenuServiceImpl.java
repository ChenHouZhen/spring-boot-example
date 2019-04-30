package com.chenhz.view.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.chenhz.view.domain.SysMenuEntity;
import com.chenhz.view.dao.SysMenuMapper;
import com.chenhz.view.entity.Menus;
import com.chenhz.view.service.SysMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author 
 * @since 2019-04-30
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    @Override
    public Menus getMenus() {


        return null;
    }

    private Menus recursionMenus(Integer parentId){
        Menus menus = new Menus();
        List<SysMenuEntity> list = this.listByParentId(parentId);
        if (CollectionUtils.isEmpty(list)){
            return menus;
        }

        return null;

    }



    private List<SysMenuEntity> listByParentId(Integer parentId){
        return this.selectList(
                new EntityWrapper<SysMenuEntity>().eq("parent_id",parentId)
        );
    }
}
