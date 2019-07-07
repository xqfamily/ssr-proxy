package com.tckj.ssrproxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shadowsocks{

    private String server;
    private String serverPort;
    private String method;
    private String password;


    public List<String> getBash(){
        List<String> commend = new ArrayList<>();
        commend.add("sslocal");
        commend.add("-s");
        commend.add(this.server);
        commend.add("-p");
        commend.add(this.serverPort);
        commend.add("-k");
        commend.add(this.password);
        commend.add("-m");
        commend.add(this.method);
        return commend;
    }

}
