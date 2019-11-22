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

package priv.raigor.spider.somedemo.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import priv.raigor.spider.utils.HttpUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cdwuchunzhi@jd.com
 * @version 1.0
 * @since 2019/11/21 21:25
 */
@Component
public class TestSpider implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(TestSpider.class);
    public void onApplicationEvent(ContextRefreshedEvent event) {
        String url = "http://ali.infosalons.com.cn/vscenter/exhibitor/exhibitorlist.aspx?f=197647d8-3195-4d62-9d67-3b44ffa33e42";
        Map<String, String> params = new HashMap<String, String>();
        params.put("__EVENTTARGET","ctl00$main$anpPager");
        params.put("__EVENTARGUMENT","2");
        params.put("__VIEWSTATE","/wEPBRI2MzcwOTk2NzA0ODI5Mzg3OTRkHgV8ObsX23MutoMJO+SjACJA+no=");
        String responseResult = HttpUtils.postForm(url , params);
        LOG.debug("responseResult is:{}",responseResult);
    }
}
