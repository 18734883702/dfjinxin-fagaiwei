package io.dfjinxin.modules.price.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.dto.PssCommTotalDto;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;
import io.dfjinxin.modules.price.dao.PssCommTotalDao;
import io.dfjinxin.modules.price.entity.PssCommTotalEntity;
import io.dfjinxin.modules.price.service.PssCommTotalService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("pssCommTotalService")
public class PssCommTotalServiceImpl extends ServiceImpl<PssCommTotalDao, PssCommTotalEntity> implements PssCommTotalService {

    @Autowired
    private PssCommTotalDao pssCommTotalDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PssCommTotalEntity> page = this.page(
                new Query<PssCommTotalEntity>().getPage(params),
                new QueryWrapper<PssCommTotalEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Map<String, List<PssCommTotalEntity>> queryCommType() {

        QueryWrapper where1 = new QueryWrapper();
        where1.eq("level_code", "0");
        where1.eq("data_flag", "0");

        List<PssCommTotalEntity> commType1 = baseMapper.selectList(where1);
        List<PssCommTotalEntity> commType2 = new ArrayList<>();
        List<PssCommTotalEntity> commType3 = new ArrayList<>();
        Map<String, List<PssCommTotalEntity>> resultMap = new HashMap<>();
        for (PssCommTotalEntity entity : commType1) {
            QueryWrapper where2 = new QueryWrapper();
            where2.in("parent_code", entity.getCommId());
            where2.eq("data_flag", "0");
            where2.eq("level_code", "1");
            List<PssCommTotalEntity> subType = baseMapper.selectList(where2);
            commType2.addAll(subType);
        }

        for (PssCommTotalEntity entity : commType2) {
            QueryWrapper where3 = new QueryWrapper();
            where3.in("parent_code", entity.getCommId());
            where3.eq("data_flag", "0");
            where3.eq("level_code", "2");
            List<PssCommTotalEntity> subType3 = baseMapper.selectList(where3);
            commType3.addAll(subType3);
        }

        //商品类型-0类 大宗，民生
        resultMap.put("commLevelCode_0", commType1);
        //商品大类-1类
        resultMap.put("commLevelCode_1", commType2);
        //商品名称-2类
        resultMap.put("commLevelCode_2", commType3);
        return resultMap;
    }

    @Override
    public PageUtils queryPageList(PssCommTotalDto pssCommTotalDto) {

        String levelCode_0 = pssCommTotalDto.getCommLevelCode_0();
        String levelCode_1 = pssCommTotalDto.getCommLevelCode_1();
        String levelCode_2 = pssCommTotalDto.getCommLevelCode_2();
        //商品类型-0类 为空 查询所有
        if (StringUtils.isBlank(levelCode_0)) {
            QueryWrapper where1 = new QueryWrapper();
            where1.eq("level_code", "0");
            where1.eq("data_flag", "0");
            List<PssCommTotalEntity> commType0 = baseMapper.selectList(where1);

            List resultList = new ArrayList();
            Map<String, List<PssCommTotalEntity>> map = new HashMap<>();
            for (PssCommTotalEntity entity : commType0) {
                //TODO
                List<PssCommTotalEntity> commType0List = selectSubCommByLevelCode0(entity.getCommId());
                map.put(entity.getCommAbb(), commType0List);
            }
            resultList.add(map);
            return new PageUtils(resultList, 0, pssCommTotalDto.getPageSize(), pssCommTotalDto.getPageIndex());

        }
        //商品类型-0类 不为空，商品大类-1类 为空，查询指定0类
        if (StringUtils.isNotBlank(levelCode_0) && StringUtils.isBlank(levelCode_1)) {
            PssCommTotalEntity commType0 = selectCommByLevelCode0(Integer.valueOf(levelCode_0));
            int countLevelCode0 = pssCommTotalDao.queryPageListCountByLevelCode0(pssCommTotalDto);
            List<PssCommTotalEntity> listLevelCode0 = pssCommTotalDao.queryPageLisByLevelCode0(pssCommTotalDto);
            commType0.setSubCommList(listLevelCode0);
            List<PssCommTotalEntity> returnList0 = new ArrayList<>();
            returnList0.add(commType0);
            return new PageUtils(returnList0, countLevelCode0, pssCommTotalDto.getPageSize(), pssCommTotalDto.getPageIndex());
        }

        //商品类型-0类 不为空，商品大类-1类 不为空，商品名称为空，查询指定1类
        if (StringUtils.isNotBlank(levelCode_0) && StringUtils.isNotBlank(levelCode_1)
                && StringUtils.isBlank(levelCode_2)) {
            PssCommTotalEntity commType0 = selectCommByLevelCode0(Integer.valueOf(levelCode_0));
            int countLevelCode1 = pssCommTotalDao.queryPageListCountByLevelCode1(pssCommTotalDto);
            List<PssCommTotalEntity> listLevelCode1 = pssCommTotalDao.queryPageLisByLevelCode1(pssCommTotalDto);
            commType0.setSubCommList(listLevelCode1);
            List<PssCommTotalEntity> returnList1 = new ArrayList<>();
            returnList1.add(commType0);
            return new PageUtils(returnList1, countLevelCode1, pssCommTotalDto.getPageSize(), pssCommTotalDto.getPageIndex());
        }

        //商品类型-0类 不为空，商品大类-1类 不为空，商品名称-2类 不为空，查询指定2类
        if (StringUtils.isNotBlank(levelCode_0) && StringUtils.isNotBlank(levelCode_1)
                && StringUtils.isNotBlank(levelCode_2)) {
            PssCommTotalEntity commType0 = selectCommByLevelCode0(Integer.valueOf(levelCode_0));
            int countLevelCode2 = pssCommTotalDao.queryPageListCountByLevelCode2(pssCommTotalDto);
            List<PssCommTotalEntity> listLevelCode2 = pssCommTotalDao.queryPageLisByLevelCode2(pssCommTotalDto);
            commType0.setSubCommList(listLevelCode2);
            List<PssCommTotalEntity> returnList2 = new ArrayList<>();
            returnList2.add(commType0);
            return new PageUtils(returnList2, countLevelCode2, pssCommTotalDto.getPageSize(), pssCommTotalDto.getPageIndex());
        }

        return null;
    }

    private List<PssCommTotalEntity> selectSubCommByLevelCode0(Integer commId) {
        if (commId == null) {
            return null;
        }
//        Map<String, List<PssCommTotalEntity>>
        QueryWrapper where2 = new QueryWrapper();
        where2.in("parent_code", commId);
        where2.eq("data_flag", "0");
        where2.eq("level_code", "1");
        // 获取一类商品
        List<PssCommTotalEntity> commLevelCode1 = baseMapper.selectList(where2);
        return null;
    }

    private PssCommTotalEntity selectCommByLevelCode0(Integer commId) {

        if (commId == null) {
            return null;
        }
        QueryWrapper where1 = new QueryWrapper();
        where1.eq("level_code", "0");
        where1.eq("data_flag", "0");
        where1.eq("comm_id", commId);
        return baseMapper.selectOne(where1);
    }

}
