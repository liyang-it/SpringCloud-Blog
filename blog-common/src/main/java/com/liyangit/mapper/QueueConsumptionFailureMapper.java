package com.liyangit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liyangit.entity.QueueConsumptionFailureEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <h2>消息消费失败记录mapper</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public interface QueueConsumptionFailureMapper extends BaseMapper<QueueConsumptionFailureEntity> {
	List<QueueConsumptionFailureEntity> queryConsumptionFailureList(@Param("queueName") String queueName);
}
