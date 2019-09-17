package io.dfjinxin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.sys.entity.SysDepEntity;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by GaoPh on 2019/9/4.
 */
public interface SysDepService extends IService<SysDepEntity> {
    /**
     * 批量新增部门信息
     */
    void addDeps(ArrayList<SysDepEntity> depEntities);

    /**
     * 查询部门信息
     */
    PageUtils queryPage(Map<String, Object> params);

}
