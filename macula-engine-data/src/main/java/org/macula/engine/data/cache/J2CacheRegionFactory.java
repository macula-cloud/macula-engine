package org.macula.engine.data.cache;

import java.util.Map;

import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.cfg.spi.DomainDataRegionBuildingContext;
import org.hibernate.cache.cfg.spi.DomainDataRegionConfig;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.hibernate.cache.spi.support.RegionFactoryTemplate;
import org.hibernate.cache.spi.support.StorageAccess;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.macula.engine.j2cache.utils.J2CacheUtils;

import org.springframework.cache.CacheManager;

/**
 * <p>自定义Hibernate二级缓存RegionFactory </p>
 * 
 */
public class J2CacheRegionFactory extends RegionFactoryTemplate {

	private static final long serialVersionUID = 1L;

	private CacheManager cacheManager;

	@Override
	protected StorageAccess createQueryResultsRegionStorageAccess(String regionName,
			SessionFactoryImplementor sessionFactory) {
		return new J2CacheDataStorageAccess(cacheManager.getCache(regionName));
	}

	@Override
	protected StorageAccess createTimestampsRegionStorageAccess(String regionName,
			SessionFactoryImplementor sessionFactory) {
		return new J2CacheDataStorageAccess(cacheManager.getCache(regionName));
	}

	@Override
	protected DomainDataStorageAccess createDomainDataStorageAccess(DomainDataRegionConfig regionConfig,
			DomainDataRegionBuildingContext buildingContext) {
		return new J2CacheDataStorageAccess(cacheManager.getCache(regionConfig.getRegionName()));
	}

	@Override
	protected void prepareForUse(SessionFactoryOptions settings, @SuppressWarnings("rawtypes") Map configValues) {
		this.cacheManager = J2CacheUtils.getCacheManager();
	}

	@Override
	protected void releaseFromUse() {
		cacheManager = null;
	}
}
