package com.ehcache.example.onheap;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.core.spi.service.StatisticsService;
import org.ehcache.core.spi.time.SystemTimeSource;
import org.ehcache.core.statistics.CacheStatistics;
import org.ehcache.impl.internal.statistics.DefaultStatisticsService;

public class OnHeapMain {
    /**
     * Ehcache management : http://www.ehcache.org/documentation/3.4/management.html
     * Ehcache Documentation : http://www.ehcache.org/documentation/3.4/
     * @param args
     */
    public static void main(String[] args) {
        // Cache Stats Example :
        // https://stackoverflow.com/questions/40453859/how-to-get-ehcache-3-1-statistics/49303449#49303449
        StatisticsService statisticsService = new DefaultStatisticsService();
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class,
                                String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10, EntryUnit.ENTRIES)))
                .using(statisticsService).build(true);

        Cache<Long, String> aCache = cacheManager.getCache("preConfigured", Long.class, String.class);
        aCache.put(1L, "one");
        aCache.put(0L, "zero");
        aCache.get(1L);
        aCache.get(0L);
        aCache.get(0L);
        aCache.get(0L);
        aCache.get(7L);


        CacheStatistics ehCacheStat = statisticsService.getCacheStatistics("preConfigured");
        System.out.println(ehCacheStat.getCacheHitPercentage());
        System.out.println(ehCacheStat.getCacheGets());

    }
}
