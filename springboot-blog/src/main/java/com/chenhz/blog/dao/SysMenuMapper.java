package com.chenhz.blog.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.chenhz.blog.domain.SysMenuEntity;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-05-01
 */
public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {
    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList();
}
