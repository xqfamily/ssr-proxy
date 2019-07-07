package com.tckj.ssrproxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @ClassName JobController
 * @Description
 * @Author icodebug
 * @Date 19-07-07 下午6:00
 */
@RestController
@RequestMapping("/ssr")
public class SsrController {


    /**
     * 测试当前的代理ip
     * @param port
     * @return
     */
    @GetMapping
    public ResponseEntity p2(Integer port){
        port = port==null?8118:port;
        try {
            System.getProperties().setProperty("proxySet", "true");
            Document document = Jsoup.connect("https://ip.gs").proxy(ShellUtil.getLocalIp(),port).ignoreHttpErrors(true).get();
            String body = document.body().text();
            return ResponseEntity.ok(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("");
    }


    /**
     * 启动代理ip
     * @return
     */
    @GetMapping("/start")
    public ResponseEntity start(){
        ShellUtil.sslocalStart();
        return ResponseEntity.ok("代理已启动");
    }

    @GetMapping("/kill")
    public ResponseEntity kill(){
        ShellUtil.sslocalStop();
        return ResponseEntity.ok("干死了");
    }

}
