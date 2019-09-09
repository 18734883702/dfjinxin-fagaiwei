package io.dfjinxin.modules.price.service.impl;

import io.dfjinxin.modules.price.dao.PssRptInfoDao;
import io.dfjinxin.modules.price.dto.PssRptConfDto;
import io.dfjinxin.modules.price.dto.PssRptInfoDto;
import io.dfjinxin.modules.price.entity.PssDatasetInfoEntity;
import io.dfjinxin.modules.price.entity.PssRptInfoEntity;
import io.dfjinxin.modules.price.service.PssRptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.price.dao.PssRptConfDao;
import io.dfjinxin.modules.price.entity.PssRptConfEntity;
import io.dfjinxin.modules.price.service.PssRptConfService;


@Service("pssRptConfService")
public class PssRptConfServiceImpl extends ServiceImpl<PssRptConfDao, PssRptConfEntity> implements PssRptConfService {

    @Override
    public PssRptConfDto saveOrUpdate(PssRptConfDto dto) {
        PssRptConfEntity entity = PssRptConfEntity.toEntity(dto);

        super.saveOrUpdate(entity);
        return PssRptConfEntity.toData(entity);
    }

    @Override
    public List<PssRptInfoDto> list(Map<String, Object> param) {
        return super.baseMapper.list(param);
    }

    @Override
    public List<String> listTemplate() {
        List<String> list = super.baseMapper.listTemplate();

        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);

            str.replace("\\", "/");
            str = str.substring(str.lastIndexOf("/") + 1);

            list.set(i, str);
        }
        return list;
    }
}
