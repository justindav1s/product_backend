package org.jnd.microservices.model.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

public class B3HeaderHelper {

    private static final Logger log = LoggerFactory.getLogger(B3HeaderHelper.class);

    public static HttpHeaders getB3Headers(HttpHeaders headers)   {
        HttpHeaders b3Headers = new HttpHeaders();

        log.debug("x-request-id : " + headers.get("x-request-id"));
        log.debug("x-b3-traceid : " + headers.get("x-b3-traceid"));
        log.debug("x-b3-spanid : " + headers.get("x-b3-spanid"));
        log.debug("x-b3-parentspanid : " + headers.get("x-b3-parentspanid"));
        log.debug("x-b3-sampled : " + headers.get("x-b3-sampled"));
        log.debug("x-b3-flags : " + headers.get("x-b3-flags"));
        log.debug("x-ot-span-context : " + headers.get("x-ot-span-context"));

        b3Headers.put("x-request-id", headers.get("x-request-id"));
        b3Headers.put("x-b3-traceid", headers.get("x-b3-traceid"));
        b3Headers.put("x-b3-spanid", headers.get("x-b3-spanid"));
        b3Headers.put("x-b3-parentspanid", headers.get("x-b3-parentspanid"));
        b3Headers.put("x-b3-sampled", headers.get("x-b3-sampled"));
        b3Headers.put("x-b3-flags", headers.get("x-b3-flags"));
        b3Headers.put("x-ot-span-context", headers.get("x-ot-span-context"));

        return b3Headers;
    }
}
