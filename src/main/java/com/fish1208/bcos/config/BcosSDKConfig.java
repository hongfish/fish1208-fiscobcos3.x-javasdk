package com.fish1208.bcos.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.InputStream;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "config-toml")
public class BcosSDKConfig {

    private Resource configPath;

    @Bean
    public BcosSDK getBcosSDK() throws Exception{
        return BcosSDK.build(getFilePath(configPath));
    }

    @Bean
    public Client getClient(BcosSDK sdk) {
        return sdk.getClient();
    }

    /**
     * 在jar包情况下无法读取配置文件config.toml，需要将配置文件config.toml先放到临时文件夹tmp下
     * @param resource
     * @return
     * @throws Exception
     */
    private String getFilePath(Resource resource) throws Exception{

        File tempFile = File.createTempFile(resource.getFilename().split("\\.")[0], "." + resource.getFilename().split("\\.")[1]);
        InputStream in = resource.getInputStream();
        try{
            log.info("config.toml, path = {}", tempFile);
            FileUtils.copyInputStreamToFile(in, tempFile);
        }finally {
            in.close();
        }
        return tempFile.getPath();
    }

    public Resource getConfigPath() {
        return configPath;
    }

    public void setConfigPath(Resource configPath) {
        this.configPath = configPath;
    }

}
