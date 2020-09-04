package com.jw.letssplit.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({
        "com.jw.letssplit.dao"
})
public class MyBatisConfig {
}
