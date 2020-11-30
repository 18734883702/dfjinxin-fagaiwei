package io.dfjinxin.modules.analyse.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.analyse.entity.WpBaseIndexValEntity;
import io.dfjinxin.modules.price.entity.PssCommTotalEntity;
import io.dfjinxin.modules.price.entity.PssPriceReltEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2019-09-02 15:38:19
 */
@Mapper
@Repository
public interface WpBaseIndexValDao extends BaseMapper<WpBaseIndexValEntity> {

    List<Map<String, Object>> queryIndexTypeByCommId(@Param("commId") Integer commId);

    List<Map<String, Object>> queryIndexTypeByCondition(@Param("condition") Map<String, Object> condition);

    List<Map<String, Object>> queryIndexTypePrice(@Param("condition") Map<String, Object> condition);

    List<WpBaseIndexValEntity> queryByIndexType(@Param("commId") Integer commId, @Param("indexType") String indexType, @Param("date") String date);

    List<WpBaseIndexValEntity> queryMapValByIndexType(@Param("commId") Integer commId,@Param("date") String date);

    List<WpBaseIndexValEntity> queryNoPriceByIndexType(@Param("commId") Integer commId, @Param("indexType") String indexType);

    IPage<PssPriceReltEntity> queryPageByDate(Page page, @Param("param") Map map);

    @Select("select val.*,total.comm_name from wp_base_index_val val \n" +
            "left join pss_comm_total total on val.comm_id = total.comm_id \n" +
            "where val.comm_id in (select comm_id from pss_comm_total \n" +
            "where data_flag=0 and parent_code = #{param.commId}) \n" +
            "and val.index_type = #{param.indexType} AND val.date >= #{param.startDate} AND val.date <= #{param.endDate} ")
    List<Map<String, Object>> downloadByDate(@Param("param") Map map);

    @Select("SELECT val.*,tol.comm_name\n" +
            "FROM wp_base_index_val val\n" +
            "left join pss_comm_total tol on val.comm_id=tol.comm_id\n" +
            "WHERE val.comm_id IN (select pss_comm_total.comm_id\n" +
            "                  FROM pss_comm_total\n" +
            "                  WHERE data_flag = 0\n" +
            "                    and parent_code = #{commId})\n" +
            "  AND val.date <= #{lastDayStr}\n" +
            "GROUP BY val.comm_id")
    List<WpBaseIndexValEntity> queryLastDayPriceByCommId(
            @Param("commId") Integer commId,
            @Param("lastDayStr") String lastDayStr);

    @Select("SELECT tol.comm_id,tol.comm_name,tol.parent_code,tol.level_code,tol.data_flag,\n" +
            "\t\t\t\t\t\t\t\ttol.create_time\n" +
            "                        FROM pss_comm_total tol\n" +
            "            left join wp_base_index_val val on val.comm_id=tol.comm_id\n" +
            "            WHERE val.comm_id IN (select pss_comm_total.comm_id\n" +
            "                              FROM pss_comm_total\n" +
            "                              WHERE data_flag = 0\n" +
            "                                and parent_code = #{commId})\n" +
            "            and val.index_type=#{indexType} \n" +
            "            group by tol.comm_id")
    List<PssCommTotalEntity> queryCommListByCommId(@Param("commId") Integer commId,
                                                   @Param("indexType") String indexType);

    @Select("SELECT val.*, tol.comm_name\n" +
            "from wp_base_index_val val\n" +
            "         left join pss_comm_total tol\n" +
            "                   on val.comm_id = tol.comm_id\n" +
            "WHERE val.comm_id = #{commId}\n" +
            "  and val.index_type = #{indexType}\n" +
            "  and tol.data_flag = 0\n" +
            "  and val.date = #{lastDayStr}\n" +
            "  and (area_name like '%省' or area_name like '%自治区' or area_name like '%市')\n")
    List<WpBaseIndexValEntity> getProvinceMapByCommId(
            @Param("commId") Integer commId,
            @Param("lastDayStr") String lastDayStr,
            @Param("indexType") String indexType);

    @Select("SELECT COUNT(1) count FROM wp_base_index_val t")
    List<Map<String, Object>> getDataCount();

    @Select("SELECT n.comm_name,t.* FROM wp_base_index_val t\n" +
            "LEFT JOIN pss_comm_total n ON t.comm_id = n.comm_id\n" +
            "WHERE n.parent_code = #{p.commId}\n" +
            "AND t.date BETWEEN #{p.startDate} AND #{p.endDate}\n" +
            "AND t.index_type =  #{p.indexType}\n" +
            "AND t.frequence = '日'\n" +
            "AND t.value > 0\n" +
            "GROUP BY t.comm_id,t.date\n" +
            "order BY t.date")
    List<Map<String, Object>> getIndexThend(@Param("p") Map<String, Object> params);

    @Select("SELECT t.* FROM wp_base_index_val t\n" +
            "LEFT JOIN pss_comm_total m ON m.comm_id = t.comm_id\n" +
            "WHERE t.index_type = '价格'\n" +
            "AND m.parent_code = #{p.commId}\n" +
            "AND t.area_name IN (SELECT p.area_name FROM wp_area_info p WHERE p.area_id BETWEEN 1 AND 32)\n" +
            "AND t.date = (SELECT MAX(s.date) FROM wp_base_index_val s WHERE s.date BETWEEN #{p.sDate} AND #{p.eDate} AND s.comm_id= 31)\n" +
            "AND t.value >0\n" +
            "GROUP BY t.area_name")
    List<Map<String, Object>> getProvince(@Param("p") Map<String, Object> params);

    @Select("SELECT t.* FROM wp_base_index_val t\n" +
            "LEFT JOIN pss_comm_total n ON n.comm_id= t.comm_id\n" +
            "WHERE t.index_type = '价格'\n" +
            "AND t.area_name = #{p.province}\n" +
            "AND n.parent_code = #{p.commId}\n" +
            "AND t.date = #{p.itemDate}\n" +
            "AND t.`value` >0")
    List<Map<String, Object>> getProvinceCommList(@Param("p") Map<String, Object> params);
}
