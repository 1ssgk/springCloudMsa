package com.msa.authservice.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RefreshScope
public class Properties {

    @Value("${wonseok.val}")
    private String val;

    public Map<String,Object> getConfig(){
        Map<String,Object> configMap = new HashMap<String ,Object>();

        configMap.put("test",val);
        return configMap;

    }

}
