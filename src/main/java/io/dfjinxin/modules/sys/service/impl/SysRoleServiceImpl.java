/**
 * 2019 东方金信
 *
 *
 *
 *
 */

package io.dfjinxin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.Constant;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;
import io.dfjinxin.modules.sys.dao.SysRoleDao;
import io.dfjinxin.modules.sys.entity.SysMenuEntity;
import io.dfjinxin.modules.sys.entity.SysRoleEntity;
import io.dfjinxin.modules.sys.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色
 *
 * @author Mark sunlightcs@gmail.com
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
	private SysRoleDao sysRoleDao;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String roleName = (String)params.get("roleName");

		IPage<SysRoleEntity> page = this.page(
			new Query<SysRoleEntity>().getPage(params),
			new QueryWrapper<SysRoleEntity>()
				.like(StringUtils.isNotBlank(roleName),"role_name", roleName)
		);

		return new PageUtils(page);
	}



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdate(SysRoleEntity role) {
 		 int roleId = role.getRoleId();
        if(roleId !=0){
			this.updateById(role);
		}else {
        	this.save(role);
		}

		//更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(int[] roleIds) {
        //删除角色
		List<Integer> rids = new ArrayList<>();
		for(int data:roleIds){
			rids.add(data);
		}
        this.removeByIds(rids);

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

    }

	@Override
	public List<Map<String,Object>> rolePerm(String role_id) {
		Map<String,Object> fmap = new HashMap<>();
		List<Map<String,Object>> privilegeDef=  sysRoleMenuService.select(fmap);
		Map<String,Object> smap = new HashMap<>();
		fmap.put("role_id", role_id);
        List<SysMenuEntity> ps = new ArrayList<>();

		List<Map<String,Object>> privilegeOfRole= sysRoleMenuService.queryMenuList(fmap);
		Map<Integer, Map<String, Object>> priDefMap = new HashMap<>();
		privilegeDef.forEach(item -> priDefMap.put((Integer)item.get("menu_id"), item));

		Map<Integer, Map<String, Object>> pri4RoleMap = new HashMap<>();
		privilegeOfRole.forEach(item -> pri4RoleMap.put((Integer)item.get("menu_id"), item));

		List<Map<String, Object>> result = new ArrayList<>();
		priDefMap.forEach((k, v) -> result.add(pri4RoleMap.getOrDefault(k, v)));


		return result;
	}

	@Override
	public void addRole(SysRoleEntity role) {

	}


	/**
	 * 检查权限是否越权
	 */
	private void checkPrems(SysRoleEntity role){
//		//如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
//		if(role.getCreateUserId() == Constant.SUPER_ADMIN){
//			return ;
//		}
//
//		//查询用户所拥有的菜单列表
//		List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());
//
//		//判断是否越权
//		if(!menuIdList.containsAll(role.getMenuIdList())){
//			throw new RRException("新增角色的权限，已超出你的权限范围");
//		}
	}
}
