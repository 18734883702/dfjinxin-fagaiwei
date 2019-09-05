package io.dfjinxin.modules.price.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.dfjinxin.modules.price.dto.PssDatasetInfoDto;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

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
	/**
	 * 
	 */
	private String dataSetName;

	/**
	 * 
	 */
	private String realSql;

	private String indeVar;
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
		to.indeVar = StringUtils.join(from.getIndeVar(), ",");
		return to;
	}

	public static PssDatasetInfoDto toData(PssDatasetInfoEntity from){
		if(null == from){
			return null;
		}
		PssDatasetInfoDto to = new PssDatasetInfoDto();
		BeanUtils.copyProperties(from, to);
		to.setIndeVar(from.indeVar.split(","));
		return to;
	}
}
