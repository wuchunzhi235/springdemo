/*
 * Copyright (c) 1998, 2019, Jd.com and/or its affiliates. All rights reserved.
 * JD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package priv.raigor.trial.spring.sourcecodeanalysis.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author cdwuchunzhi@jd.com
 * @version 1.0
 * @since 2019/11/14 21:13
 */

@Component(value = "chatMessageHandlerImpl")
public class AppHandlerService implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(AppHandlerService.class);

    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info(" here is ???? ");
    }
}
