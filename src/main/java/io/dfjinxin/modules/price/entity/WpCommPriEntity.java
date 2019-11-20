package io.dfjinxin.modules.price.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *modify by zhc 11.14
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2019-08-27 17:23:11
 */
@Data
@TableName("wp_comm_pri")
public class WpCommPriEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String indexName;

	private Integer indexId;

	private BigDecimal value;

	private String unit;

	private Date dataTime;

	private String areaName;

}
