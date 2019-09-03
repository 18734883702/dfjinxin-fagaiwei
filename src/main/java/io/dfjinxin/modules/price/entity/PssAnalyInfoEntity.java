package io.dfjinxin.modules.price.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.dfjinxin.modules.price.dto.PssAnalyInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author bourne
 * @email kuibobo@gmail.com
 * @date 2019-09-02 17:03:24
 */
@Data
@TableName("pss_analy_info")
@ApiModel(value = "分析信息对象")
public class PssAnalyInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    @ApiModelProperty(value = "分析id", name = "analyId", required = true)
    private Integer analyId;
    /**
     *
     */
    @ApiModelProperty(value = "分析名称", name = "analyName", required = true)
    private String analyName;
    /**
     *
     */
    @ApiModelProperty(value = "分析类型", name = "analyWay", required = true)
    private String analyWay;
    /**
     *
     */
    @ApiModelProperty(value = "数据集id", name = "dataSetId", required = true)
    private Integer dataSetId;
    /**
     *
     */
    @ApiModelProperty(value = "自变量", name = "indeVar", required = true)
    private String indeVar;
    /**
     *
     */
    @ApiModelProperty(value = "因变量", name = "depeVar", required = true)
    private String depeVar;
    /**
     *
     */
    @ApiModelProperty(value = "分析描述", name = "remarks", required = true)
    private String remarks;
    /**
     *
     */
    @ApiModelProperty(value = "创建时间", name = "crteTime", required = true)
    private Date crteTime;

    public static PssAnalyInfoEntity toEntity(PssAnalyInfoDto from) {
        if (null == from) {
            return null;
        }
        PssAnalyInfoEntity to = new PssAnalyInfoEntity();
        BeanUtils.copyProperties(from, to);
        return to;
    }

    public static PssAnalyInfoDto toData(PssAnalyInfoEntity from){
        if(null == from){
            return null;
        }
        PssAnalyInfoDto to = new PssAnalyInfoDto();
        BeanUtils.copyProperties(from, to);
        return to;
    }
}
