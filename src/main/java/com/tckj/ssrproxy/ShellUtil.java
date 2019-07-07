package com.tckj.ssrproxy;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ShellUtil
 * @Description
 * @Author icodebug
 * @Date 19-7-07 下午6:15
 */
public class ShellUtil {

    /**
     * 干掉ssr代理
     */
    public static void sslocalStop(){
        try {
            ProcessBuilder builder = new ProcessBuilder("bash", "-c","kill -9 `pidof python`");
            System.out.println("kill builder:"+builder.toString());
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动ssr代理
     */
    public static void sslocalStart(){
        try {
            List<String> commend = BaseInitalizer.getSsrBash();
            commend.add("-b");
            commend.add(getLocalIp());
            commend.add("-l");
            commend.add("1080");
            commend.add("-d");
            commend.add("start");
            if (commend!=null){
                ProcessBuilder builder = new ProcessBuilder(commend);
                builder.command(commend);
                Process p = builder.start();
                doWaitFor(p);
                p.destroy();
            }else {
                System.out.println("没有获取到可切换的ssr代理");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void startPrivoxy(){
        try {
            List<String> commend = new ArrayList<>();
            commend.add("service");
            commend.add("privoxy");
            commend.add("start");

            ProcessBuilder builder = new ProcessBuilder(commend);
            builder.command(commend);

            System.out.println("启动 privoxy.service");
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置privoxy的配置文件
     */
    public static void setPrivoxyConfig(){
        try {
            System.out.println("配置 privoxy config");
            ProcessBuilder builder = new ProcessBuilder("bash", "-c","echo 'forward-socks5 / "+getLocalIp()+":1080 .'>>/etc/privoxy/config");
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取当前本机ip
     * @return
     */
    public static String getLocalIp(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }

    private static int doWaitFor(Process p){
        InputStream in = null;
        InputStream err = null;
        int exitValue = -1; // returned to caller when p is finished
        try {
            in = p.getInputStream();
            err = p.getErrorStream();
            boolean finished = false; // Set to true when p is finished

            while (!finished) {
                try {
                    while (in.available() > 0) {
                        // Print the output of our system call
                        BufferedInputStream bi = new BufferedInputStream(in);
                        Character c = new Character((char) bi.read());
                    }
                    while (err.available() > 0) {
                        // Print the output of our system call
                        BufferedInputStream bi = new BufferedInputStream(err);
                        Character c = new Character((char) bi.read());
                    }

                    exitValue = p.exitValue();
                    finished = true;

                } catch (IllegalThreadStateException e) {
                    // Process is not finished yet;
                    // Sleep a little to save on CPU cycles
                    Thread.currentThread().sleep(500);
                }
            }
        } catch (Exception e) {
            // unexpected exception! print it out for debugging...
            System.err.println("doWaitFor();: unexpected exception - "
                    + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        // return completion status to caller
        return exitValue;
    }

}
