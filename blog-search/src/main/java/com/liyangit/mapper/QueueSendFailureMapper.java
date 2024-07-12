package com.liyangit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liyangit.entity.QueueConsumptionFailureEntity;
import com.liyangit.entity.QueueSendFailureEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <h2>消息发送失败记录mapper</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public interface QueueSendFailureMapper extends BaseMapper<QueueSendFailureEntity> {
	
	List<QueueSendFailureEntity> querySendFailureList(@Param("queueName") String queueName);
}
