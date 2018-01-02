package com.shutafin.system;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by usera on 1/2/2018.
 */
@SpringBootApplication(scanBasePackages = {
        "com.shutafin.controller.*",
        "com.shutafin.service.*"})
public class GatewayTestApplication {
}
