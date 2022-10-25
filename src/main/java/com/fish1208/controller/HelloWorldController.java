package com.fish1208.controller;

import com.alibaba.fastjson.JSON;
import com.fish1208.common.response.Result;
import com.fish1208.contract.HelloWorld;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/contract/helloWorld")
public class HelloWorldController {

    @Autowired
    private HelloWorld helloWorld;

    /**
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result<?> get() throws Exception {
        if (helloWorld != null) {
            log.info("HelloWorld address is: {}", helloWorld.getContractAddress());
            String result = helloWorld.get();
            return Result.data(result);
        }
        return Result.fail("执行HelloWorld合约失败");
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public Result<?> set(@RequestBody Map<String,Object> param) throws Exception {
        if (helloWorld != null) {
            log.info("HelloWorld address is: {}", helloWorld.getContractAddress());
            String n = (String)param.get("n");
            TransactionReceipt receipt = helloWorld.set(n);
            log.info("HelloWorld receipt = {}", JSON.toJSONString(receipt));
            return Result.data(receipt);
        }
        return Result.fail("执行HelloWorld合约失败");
    }
}
