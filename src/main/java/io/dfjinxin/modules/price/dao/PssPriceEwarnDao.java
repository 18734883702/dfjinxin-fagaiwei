package io.dfjinxin.modules.price.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.price.entity.PssPriceEwarnEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2019-08-26 17:21:32
 */
@Mapper
@Repository
public interface PssPriceEwarnDao extends BaseMapper<PssPriceEwarnEntity> {

    List<PssPriceEwarnEntity> queryEchartsData(@Param("commId") Integer commId,
                                               @Param("ewarnTypeId") Integer ewarnTypeId,
                                               @Param("startDateStr") String startDateStr,
                                               @Param("endDateStr") String endDateStr);

    List queryType3Warn();

    List<PssPriceEwarnEntity> queryEwarnlevel();

    PssPriceEwarnEntity selectMaxRange(
            @Param("commId") Integer commId,
            @Param("dateStr") String dateStr);

//    PssPriceEwarnEntity selectMaxDateTimeEntiey(@Param("commId") Integer commId);

    List<PssPriceEwarnEntity> queryPriceEwarnByCommId(@Param("commId") Integer commId);

    List<PssPriceEwarnEntity> queryPriceEwarnByDate(@Param("commId") Integer commId,
                                                    @Param("dateStr") String dateStr);

    List<Map<Integer, Object>> countEwarn(@Param("dateStr") String dateStr);
}
