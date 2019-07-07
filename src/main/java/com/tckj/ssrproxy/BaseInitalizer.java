package com.tckj.ssrproxy;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BaseInitalizer
 * @Description
 * @Author icodebug
 * @Date 19-07-07 下午3:05
 */
@Component
public class BaseInitalizer {

    private static List<Shadowsocks> ssrs = new ArrayList<>();

    @Autowired
    public BaseInitalizer() {
        System.out.println("BaseInitalizer初始化信息！！！！！");
        ShellUtil.setPrivoxyConfig();
        ShellUtil.startPrivoxy();
        initSsr();
    }

    public void initSsr(){
        if (ssrs.size()<1){/*todo 这里增加自己的ssr代理节点信息*/
            ssrs.add(new Shadowsocks("xxx.xxx.com","1111","rc4-md5","1111"));
            ssrs.add(new Shadowsocks("xxx.xxx.com","1111","rc4-md5","1111"));
            ssrs.add(new Shadowsocks("xxx.xxx.com","1111","rc4-md5","1111"));
            ssrs.add(new Shadowsocks("xxx.xxx.com","1111","rc4-md5","1111"));

        }
    }

    public static List<String> getSsrBash(){
        if (ssrs.size()>0){
            return ssrs.get(RandomUtils.nextInt(0,ssrs.size()-1)).getBash();
        }
        return null;
    }

}
