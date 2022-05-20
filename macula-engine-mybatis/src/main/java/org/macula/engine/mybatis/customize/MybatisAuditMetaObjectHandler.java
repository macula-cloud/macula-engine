package org.macula.engine.mybatis.customize;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.function.Supplier;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.macula.engine.assistant.provider.PrincipalAssistantProvider;
import org.macula.engine.mybatis.constants.MybatisPlusConstants;

import org.springframework.util.StringUtils;

/**
 * <p>Fullfill Mybatis AuditField Fields</p>
 */
public class MybatisAuditMetaObjectHandler implements MetaObjectHandler {

	private Set<String> createTimeNames;
	private Set<String> createByNames;
	private Set<String> lastUpdateTimeNames;
	private Set<String> lastUpdateByNames;

	private final PrincipalAssistantProvider principalProvider;

	public MybatisAuditMetaObjectHandler(Properties properties, PrincipalAssistantProvider principalProvider) {
		this.createTimeNames = getPropertySet(properties, MybatisPlusConstants.AUDIT_CREATE_TIME_PROPERTY_NAME);
		this.createByNames = getPropertySet(properties, MybatisPlusConstants.AUDIT_CREATE_BY_PROPERTY_NAME);
		this.lastUpdateTimeNames = getPropertySet(properties, MybatisPlusConstants.AUDIT_UPDATE_TIME_PROPERTY_NAME);
		this.lastUpdateByNames = getPropertySet(properties, MybatisPlusConstants.AUDIT_UPDATE_BY_PROPERTY_NAME);
		this.principalProvider = principalProvider;
	}

	@Override
	public void insertFill(MetaObject metaObject) {
		createTimeNames.forEach(field -> strictInsertFill(metaObject, field, () -> LocalDateTime.now(), LocalDateTime.class));
		createByNames.forEach(field -> strictInsertFill(metaObject, field, String.class, getUserName()));
		lastUpdateTimeNames.forEach(field -> strictInsertFill(metaObject, field, () -> LocalDateTime.now(), LocalDateTime.class));
		lastUpdateByNames.forEach(field -> strictInsertFill(metaObject, field, String.class, getUserName()));
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		lastUpdateTimeNames.forEach(field -> strictUpdateFill(metaObject, field, () -> LocalDateTime.now(), LocalDateTime.class));
		lastUpdateByNames.forEach(field -> strictUpdateFill(metaObject, field, String.class, getUserName()));
	}

	@Override
	public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName, Supplier<?> fieldVal) {
		if (CollectionUtils.containsAny(createTimeNames, fieldName)
			|| CollectionUtils.containsAny(createByNames, fieldName)
			|| CollectionUtils.containsAny(lastUpdateTimeNames, fieldName)
			|| CollectionUtils.containsAny(lastUpdateByNames, fieldName)) {
			// 审计字段不管是否原来存在都要替换
			Object obj = fieldVal.get();
			if (Objects.nonNull(obj)) {
				metaObject.setValue(fieldName, obj);
			}
			return this;
		}

		return MetaObjectHandler.super.strictFillStrategy(metaObject, fieldName, fieldVal);
	}

	/**
	 * 获取 spring security 当前的用户名
	 * @return 当前用户名
	 */
	private String getUserName() {
		if (principalProvider != null && (principalProvider.getPrincipal() != null)) {
			return principalProvider.getPrincipal().getName();
		}
		return "*SYSADM";
	}

	/** Get Mybatis Properties Settings */
	private Set<String> getPropertySet(Properties properties, String property) {
		String value = properties.getProperty(property);
		return value == null ? null : StringUtils.commaDelimitedListToSet(value);
	}
}
