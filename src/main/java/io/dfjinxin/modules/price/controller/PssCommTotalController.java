package io.dfjinxin.modules.price.controller;

import io.dfjinxin.common.dto.PssCommTotalDto;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.price.entity.PssCommConfEntity;
import io.dfjinxin.modules.price.entity.PssCommTotalEntity;
import io.dfjinxin.modules.price.service.PssCommConfService;
import io.dfjinxin.modules.price.service.PssCommTotalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 价格监测子系统-价格预警-价格预警配置-商品配置Controller
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2019-08-23 15:38:58
 */
@RestController
@RequestMapping("price/comm/conf")
@Api(tags = "PssCommTotalController", description = "价格监测子系统-商品配置")
public class PssCommTotalController {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private PssCommTotalService pssCommTotalService;

    @Autowired
    private PssCommConfService pssCommConfService;

    @GetMapping("/getCommType")
    @ApiOperation("商品配置-获取商品类型&商品大类")
    public R getCommType() {
        Map<String, List<PssCommTotalEntity>> result = pssCommTotalService.queryCommType();
        return R.ok().put("data", result);
    }

    @PostMapping("/queryPageList")
    @ApiOperation("商品预警配置-查询")
    public R queryPageList(@RequestBody(required = false) PssCommTotalDto params) {
        LOG.info("商品预警配置-查询,请求参数:{}", params);
        params.startNumber();
        PageUtils page = pssCommTotalService.queryPageList(params);
        return R.ok().put("data", page);
    }

    @PostMapping("/queryCommInfoPageList")
    @ApiOperation("商品信息-查询")
    public R queryCommInfoPageList(@RequestBody(required = false) PssCommTotalDto params) {

        params.startNumber();
        PageUtils page = pssCommTotalService.queryCommInfoPageList(params);
        return R.ok().put("data", page);
    }

    @PostMapping("/getSubCommByCommId/{commId}")
    @ApiOperation("根据指定商品id查询该商品的下级商品")
    public R getSubCommByCommId(@PathVariable("commId") Integer commId) {

        List<PssCommTotalEntity> commList = pssCommTotalService.getSubCommByCommId(commId);
        return R.ok().put("data", commList);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "商品配置-保存配置", notes = "commId:4类商品id,ewarnIds:预警id列表,indexIds:商品对应指标类型为价格的列表 .eg:{\"commId\":172, \"ewarnIds\":[3,8],\"indexIds\":[39,40,41] } ")
    public R save(@RequestBody Map<String, Object> params) {
        if (params.isEmpty() || params.size() == 0) {
            R.error("请求参数为空!");
        }
        Integer commId = (Integer) params.get("commId");
        List<Integer> ewarnIds = (List<Integer>) params.get("ewarnIds");
        List<Integer> indexIds = (List<Integer>) params.get("indexIds");
        if (commId == null || ewarnIds == null || indexIds == null) {
            return R.error("请求参数为空!");
        }

        List<PssCommConfEntity> commConfEntityList = pssCommConfService.getCommConfByParms(commId, ewarnIds, indexIds);
        if (commConfEntityList != null && commConfEntityList.size() > 0) {
            return R.error("该商品已配置此种类型预警!");
        }
        pssCommConfService.saveCommConf(commId, ewarnIds, indexIds);
        return R.ok();
    }

    @PostMapping("/queryAll")
    @ApiOperation("商品配置-查询所有商品")
    public R queryPageList() {
        List<PssCommTotalEntity> list = pssCommTotalService.getAll();
        return R.ok().put("data", list);
    }

}
