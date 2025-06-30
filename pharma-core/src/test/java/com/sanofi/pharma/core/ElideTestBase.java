package com.sanofi.pharma.core;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableRetry
//@Transactional
public class ElideTestBase {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected EntityManager em;

//    public ResultActions performGet(String url) throws Exception {
//        return mockMvc.perform(get(url)
//                .accept("application/vnd.api+json"));
//    }

    public ResultActions performGet(String url) throws Exception {
        return mockMvc.perform(get(url)
                        .contentType("application/vnd.api+json")
                        .accept("application/vnd.api+json"))
                .andDo(print());  // 打印请求详情（测试时可启用）
    }
}
