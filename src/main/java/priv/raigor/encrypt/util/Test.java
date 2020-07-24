/*
 * Copyright (c) 1998, 2020, Jd.com and/or its affiliates. All rights reserved.
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

package priv.raigor.encrypt.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;

/**
 * @author cdwuchunzhi@jd.com
 * @version 1.0
 * @since 2020/5/8 17:03
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {

        RateLimiter limiter = RateLimiter.create(10.0);
        Thread.sleep(1000);
        for(int i = 1; i < 30; i++ ) {
            if(limiter.tryAcquire()){
                System.out.println("cutTime=" + System.currentTimeMillis() +"get tryAcquire sucess");
            }else{
                System.out.println("cutTime=" + System.currentTimeMillis() +"get tryAcquire fail");
            }


        }

    }


}
