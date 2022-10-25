package com.fish1208.bcos.config;

import com.fish1208.bcos.ContractAddress;
import com.fish1208.contract.*;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.client.Client;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "contract-address")
public class ContractConfig {

    private String helloworld;

    @Bean
    public HelloWorld loadHelloWorld(Client client){
        return HelloWorld.load(helloworld, client, client.getCryptoSuite().getCryptoKeyPair());
    }

    @Bean
    public ContractAddress setAddress(){
        log.info("helloworld={}", helloworld);
        ContractAddress contractAddress = new ContractAddress();
        contractAddress.setHelloworld(helloworld);
        return contractAddress;
    }

    public String getHelloworld() {
        return helloworld;
    }

    public void setHelloworld(String helloworld) {
        this.helloworld = helloworld;
    }
}
