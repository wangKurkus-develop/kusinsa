package com.kurkus.kusinsa.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurkus.kusinsa.DockerComposeContainerInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {DockerComposeContainerInitializer.class})
public class RankIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private final String PREFIX_URI = "/ranks";

    @Test
    public void orderRank() throws Exception {
        // given
        String URI = PREFIX_URI+"/order";
        // when
        ResultActions result = mockMvc.perform(get(URI)).andDo(print());
        // then
        result.andExpect(status().is2xxSuccessful());
    }

    @Test
    public void clickRank() throws Exception {
        // given
        String URI = PREFIX_URI+"/click";
        // when
        ResultActions result = mockMvc.perform(get(URI)).andDo(print());
        // then
        result.andExpect(status().is2xxSuccessful());
    }
}
