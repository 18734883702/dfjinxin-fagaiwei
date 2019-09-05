package io.dfjinxin.modules.price.service.impl;

import org.springframework.stereotype.Service;
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
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PssRptConfEntity> page = this.page(
                new Query<PssRptConfEntity>().getPage(params),
                new QueryWrapper<PssRptConfEntity>()
        );

        return new PageUtils(page);
    }

}
