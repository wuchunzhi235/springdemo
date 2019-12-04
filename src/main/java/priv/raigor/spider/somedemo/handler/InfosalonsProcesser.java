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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cdwuchunzhi@jd.com
 * @version 1.0
 * @since 2019/12/3 20:37
 */


/**
 * @author cdwuchunzhi@jd.com
 * @version 1.0
 * @since 2019/11/26 21:11
 */
public class InfosalonsProcesser implements PageProcessor {

    //private Site site = Site.me().setDomain("my.oschina.net");

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {
        //List<String> links = page.getHtml().links().regex("http://my\\.oschina\\.net/flashsword/blog/\\d+").all();

        //page.addTargetRequests(links);
        //page.putField("title", page.getHtml().xpath("//table[@id='tbVisitor']/span[@class='popmsg']").toString());
        String test = page.getHtml().xpath("//table[@id='tbVisitor']/span[@class='popmsg']").toString();
        System.out.println(test);
        page.putField("content", page.getHtml().$("div.content").toString());
        page.putField("tags",page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all());
    }

    public Site getSite() {
        return site;

    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new OschinaBlogPageProcesser())
                //.addUrl("http://my.oschina.net/flashsword/blog")
                //.addRequest()
                .addPipeline(new ConsolePipeline());//.run();

        String url = "http://ali.infosalons.com.cn/vscenter/exhibitor/exhibitorlist.aspx?f=197647d8-3195-4d62-9d67-3b44ffa33e42";
        Map<String, Object> nameValuePair = new HashMap<String, Object>();
        NameValuePair[] values = new NameValuePair[5];
        values[0] = new BasicNameValuePair("__EVENTTARGET", "ctl00$main$anpPager");
        values[1] = new BasicNameValuePair("__EVENTARGUMENT", "2");
        values[2] = new BasicNameValuePair("__VIEWSTATE", "/wEPBRI2MzcwOTk2NzA0ODI5Mzg3OTRkHgV8ObsX23MutoMJO+SjACJA+no=");
        values[3] = new BasicNameValuePair("ctl00$main$sm", "ctl00$main$upPager|ctl00$main$anpPager");
        values[4] = new BasicNameValuePair("__ASYNCPOST", "true");
        //values[4] = new BasicNameValuePair("__ASYNCPOST", "true");



        nameValuePair.put("nameValuePair", values);
        Request request = new Request(url);
        request.setExtras(nameValuePair);
        request.setMethod(HttpConstant.Method.POST);

        spider.addRequest(request);

        spider.run();
    }
}