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

package priv.raigor.gui.javaFX;

import java.util.UUID;

/**
 * @author cdwuchunzhi@jd.com
 * @version 1.0
 * @since 2020/4/26 23:18
 */
public class ChatTools {

    public static String getUUid(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public static long getTimeStampCurrent(){
        return System.currentTimeMillis();
    }


    public static double getRandom(){
        return Math.random();
    }
}
