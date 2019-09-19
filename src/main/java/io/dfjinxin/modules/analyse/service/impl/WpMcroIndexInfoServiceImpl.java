package io.dfjinxin.modules.analyse.service.impl;

import io.dfjinxin.common.dto.CountAndProvinceDto;
import io.dfjinxin.common.utils.DateUtils;
import io.dfjinxin.modules.analyse.dao.WpMcroIndexValDao;
import io.dfjinxin.modules.analyse.entity.WpMcroIndexValEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.metastore.api.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.analyse.dao.WpMcroIndexInfoDao;
import io.dfjinxin.modules.analyse.entity.WpMcroIndexInfoEntity;
import io.dfjinxin.modules.analyse.service.WpMcroIndexInfoService;


@Service("wpMcroIndexInfoService")
public class WpMcroIndexInfoServiceImpl extends ServiceImpl<WpMcroIndexInfoDao, WpMcroIndexInfoEntity> implements WpMcroIndexInfoService {


    @Autowired
    private WpMcroIndexValDao wpMcroIndexValDao;

    @Override
    public List<Map<String, Object>> getAreaName() {
        QueryWrapper where1 = new QueryWrapper();
        where1.eq("stat_area_id", 0);
        where1.groupBy("stat_area_name");
        QueryWrapper where2 = new QueryWrapper();
        where2.eq("stat_area_id", 1);
        where2.groupBy("stat_area_name");
        List<WpMcroIndexValEntity> listType0 = wpMcroIndexValDao.selectList(where1);
        List<WpMcroIndexValEntity> listType1 = wpMcroIndexValDao.selectList(where2);
        Map<String, List<CountAndProvinceDto>> map = new HashMap<>();

        List<CountAndProvinceDto> dtoList0 = new ArrayList<>();
        for (WpMcroIndexValEntity type0 : listType0) {
            CountAndProvinceDto dto = new CountAndProvinceDto();
            dto.setStatAreaId(type0.getStatAreaId());
            dto.setStatAreaName(type0.getStatAreaName());
            List<Map<String,Object>> indexNameList = wpMcroIndexValDao.selectdistinctIndexName(type0.getStatAreaName(), type0.getIndexId());
            dto.setIndexNameList(indexNameList);
            dtoList0.add(dto);
        }

        List<CountAndProvinceDto> dtoList1 = new ArrayList<>();
        for (WpMcroIndexValEntity type1 : listType1) {
            CountAndProvinceDto dto = new CountAndProvinceDto();
            dto.setStatAreaId(type1.getStatAreaId());
            dto.setStatAreaName(type1.getStatAreaName());
            List<Map<String,Object>> indexNameList = wpMcroIndexValDao.selectdistinctIndexName(type1.getStatAreaName(), type1.getIndexId());
            dto.setIndexNameList(indexNameList);
            dtoList1.add(dto);
        }

        map.put("guonei", dtoList0);
        map.put("guoji", dtoList1);
        List retList = new ArrayList();
        retList.add(map);
        return retList;
    }

    @Override
    public List<WpMcroIndexValEntity> queryIndexVals(String areaName,String indexId, String dateFrom, String dateTo) {

        QueryWrapper where = new QueryWrapper();
        where.eq("index_id", indexId);
        where.eq("stat_area_name", areaName);
        if (StringUtils.isNotBlank(dateFrom) && StringUtils.isNotBlank(dateTo)) {
            where.between("data_time", dateFrom, dateTo);
        } else {
            //默认取最近三年
            where.between("data_time", DateUtils.getLastYearByVal(20), DateUtils.getCurrentDayStr());
        }
        List<WpMcroIndexValEntity> list = wpMcroIndexValDao.selectList(where);
        return list;
    }

}
