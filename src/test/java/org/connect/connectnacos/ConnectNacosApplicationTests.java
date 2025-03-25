package org.connect.connectnacos;

import org.connect.connectnacos.service.NacosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConnectNacosApplicationTests {
    @Autowired
    private NacosService nacosService;

    @Test
    void registerService() {
        nacosService.registerService();
    }

    @Test
    void deregisterService() {
        nacosService.deregisterService();
    }

}
