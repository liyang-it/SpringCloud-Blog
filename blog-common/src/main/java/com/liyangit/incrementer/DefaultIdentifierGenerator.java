package com.liyangit.incrementer;

import java.net.InetAddress;

/**
 * <h2>默认id生成器</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public class DefaultIdentifierGenerator {
	
	private final Sequence sequence;
	
	/**
	 * @see #getInstance()
	 * @deprecated 3.5.3.2 共享默认单例
	 */
	@Deprecated
	public DefaultIdentifierGenerator() {
		this.sequence = new Sequence(null);
	}
	
	public DefaultIdentifierGenerator(InetAddress inetAddress) {
		this.sequence = new Sequence(inetAddress);
	}
	
	public DefaultIdentifierGenerator(long workerId, long dataCenterId) {
		this.sequence = new Sequence(workerId, dataCenterId);
	}
	
	public DefaultIdentifierGenerator(Sequence sequence) {
		this.sequence = sequence;
	}
	
	public static DefaultIdentifierGenerator getInstance() {
		return DefaultInstance.INSTANCE;
	}
	
	public Long nextId(Object entity) {
		return sequence.nextId();
	}
	
	private static class DefaultInstance {
		
		public static final DefaultIdentifierGenerator INSTANCE = new DefaultIdentifierGenerator();
		
	}
}
