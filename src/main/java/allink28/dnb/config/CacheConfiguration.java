package allink28.dnb.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(allink28.dnb.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(allink28.dnb.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(allink28.dnb.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(allink28.dnb.domain.Character.class.getName(), jcacheConfiguration);
            cm.createCache(allink28.dnb.domain.Character.class.getName() + ".spells", jcacheConfiguration);
            cm.createCache(allink28.dnb.domain.Spell.class.getName(), jcacheConfiguration);
            cm.createCache(allink28.dnb.domain.Campaign.class.getName(), jcacheConfiguration);
            cm.createCache(allink28.dnb.domain.Campaign.class.getName() + ".players", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
