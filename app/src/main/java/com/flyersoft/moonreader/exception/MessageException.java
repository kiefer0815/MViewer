/*
 * Copyright (c) 2015,Deepspring Inc. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This product is protected by copyright and distributed under
 * licenses restricting copying, distribution, and decompilation.
 */

package com.flyersoft.moonreader.exception;

/**
 * User: zhaohaifeng
 * Date: 14-10-13
 * Time: 下午10:05
 */
public class MessageException extends RuntimeException {

    public MessageException(String s) {
        super(s);
    }

    public MessageException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public MessageException(Throwable throwable) {
        super(throwable);
    }
}