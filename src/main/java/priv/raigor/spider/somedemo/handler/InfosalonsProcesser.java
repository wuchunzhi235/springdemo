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

import org.apache.commons.collections.map.HashedMap;
import org.apache.http.NameValuePair;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
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
        String test = page.getHtml().xpath("//table[@id='tbVisitor']").toString();
        System.out.println(test);
        page.putField("content", page.getHtml().$("div.content").toString());
        page.putField("tags",page.getHtml().xpath("//div[@class='BlogTags']/a/text()").all());
    }

    public Site getSite() {
        return site;

    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new InfosalonsProcesser())
                //.addUrl("http://my.oschina.net/flashsword/blog")
                //.addRequest()
                .addPipeline(new ConsolePipeline());//.run();

        String url = "http://ali.infosalons.com.cn/vscenter/exhibitor/exhibitorlist.aspx?f=197647d8-3195-4d62-9d67-3b44ffa33e42";


        Map<String,Object> params = new HashedMap();
        params.put("__EVENTTARGET","ctl00$main$anpPager");
        params.put("__EVENTARGUMENT","2");
        params.put("__VIEWSTATE","/wEPBRI2MzcwOTk2NzA0ODI5Mzg3OTRkHgV8ObsX23MutoMJO+SjACJA+no=");
        params.put("ctl00$main$sm","ctl00$main$upPager|ctl00$main$anpPager");
        params.put("__ASYNCPOST","true");

        Request request = new Request(url);
        request.setMethod(HttpConstant.Method.POST);

        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        BasicClientCookie cookie = new BasicClientCookie("PHPSESSID", "2brls80t268lhm9vhmadvkbko4");
        request.addHeader("Cookies", cookie.toString());
        request.setRequestBody(HttpRequestBody.form(params, "utf-8"));
        spider.addRequest(request);

        spider.run();
    }
}