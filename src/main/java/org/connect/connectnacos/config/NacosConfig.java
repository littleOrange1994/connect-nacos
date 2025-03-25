package org.connect.connectnacos.config;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fancheng.zeng
 * @title
 * @email Fancheng.Zeng@geely.com
 * @create 2025/3/24
 * @desc
 */
@Configuration
public class NacosConfig {
    @Value("${nacos.server-addr}")
    private String serverAddr;

    @Bean
    public NamingService namingService(){
        try {
            return NamingFactory.createNamingService(serverAddr); // 创建并返回单例的 NamingService 实例
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }
}
