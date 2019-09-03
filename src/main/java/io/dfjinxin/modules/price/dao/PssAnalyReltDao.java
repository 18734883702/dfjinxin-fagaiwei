package io.dfjinxin.modules.price.dao;

import io.dfjinxin.modules.price.entity.PssAnalyReltEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author bourne
 * @email kuibobo@gmail.com
 * @date 2019-09-02 17:03:24
 */
@Mapper
public interface PssAnalyReltDao extends BaseMapper<PssAnalyReltEntity> {
    List<PssAnalyReltEntity> queryPage(@Param("param")Map map);
}
