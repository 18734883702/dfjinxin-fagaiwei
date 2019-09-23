package io.dfjinxin.modules.price.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.dfjinxin.modules.price.dto.PssDatasetInfoDto;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 *
 *
 * @author bourne
 * @email kuibobo@gmail.com
 * @date 2019-09-02 17:05:57
 */
@Data
@TableName("pss_dataset_info")
public class PssDatasetInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer dataSetId;

	private Integer dataSetType;
	/**
	 *
	 */
	private String dataSetName;

	/**
	 *
	 */
	private String tabName;

	private String indeVal;
	/**
	 *
	 */
	private Date crteTime;


	public static PssDatasetInfoEntity toEntity(PssDatasetInfoDto from) {
		if (null == from) {
			return null;
		}
		PssDatasetInfoEntity to = new PssDatasetInfoEntity();
		BeanUtils.copyProperties(from, to);

		to.crteTime = new Date();
		to.indeVal = JSON.toJSONString(from.getColumnVal());
		return to;
	}

	public static PssDatasetInfoDto toData(PssDatasetInfoEntity from){
		if(null == from){
			return null;
		}
		PssDatasetInfoDto to = new PssDatasetInfoDto();
		BeanUtils.copyProperties(from, to);
		to.setColumnVal(JSONObject.parseObject(from.indeVal, new TypeReference<Map<String, Object>>(){}));
		return to;
	}
}
