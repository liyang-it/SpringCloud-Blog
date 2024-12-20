package com.liyangit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * <h2>消息发送失败记录表</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@TableName(value = "t_queue_send_failure")
public class QueueSendFailureEntity {
	@TableId(type = IdType.INPUT)
	private String messageId;
	private String message;
	private String queueName;
	private String failureReason;
	private Boolean status;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	
	public String getMessageId() {
		return messageId;
	}
	
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getQueueName() {
		return queueName;
	}
	
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	
	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}
	
	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
}
