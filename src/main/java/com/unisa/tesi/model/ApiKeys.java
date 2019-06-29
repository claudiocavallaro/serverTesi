package com.unisa.tesi.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ApiKeys {

    private String host = "identify-eu-west-1.acrcloud.com";
    private String accessKey = "d4d377f0c6947376606087d7c765a228";
    private String accessSecret = "IHA8k41R2Iv24nmEtRTABtcRH2x1Fbw5IQ57fJwO";

    @JsonCreator
    public ApiKeys(){}

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }
}
