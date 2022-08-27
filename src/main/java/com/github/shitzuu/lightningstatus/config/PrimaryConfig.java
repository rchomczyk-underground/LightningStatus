package com.github.shitzuu.lightningstatus.config;

import com.github.shitzuu.lightningstatus.subject.implementation.HttpMonitoredSubject;
import com.github.shitzuu.lightningstatus.subject.implementation.PingMonitoredSubject;
import com.github.shitzuu.lightningstatus.crawler.implementation.HttpCrawlerMethod;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PrimaryConfig extends OkaeriConfig {

    @Comment("This should be delay of checking status of watchable objects. (in minutes)")
    public Duration monitorInterval = Duration.ofMinutes(5);

    @Comment("This should be the list of watchable objects which should be checked by HttpWatcher.")
    public List<HttpMonitoredSubject> httpMonitoredSubjects = Collections.singletonList(
        new HttpMonitoredSubject("github's website", "https://github.com", HttpCrawlerMethod.GET, 200, 299, Collections.emptyMap()));

    @Comment("This should be the list of watchable objects which should be checked by PingWatcher.")
    public List<PingMonitoredSubject> pingMonitoredSubjects = Collections.emptyList();
}
