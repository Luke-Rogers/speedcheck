package com.speedcheck.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.speedcheck.service.SpeedTestService;
import com.speedcheck.transfer.Filters;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ResultsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void should_get_all_results() throws Exception {
        mvc.perform(get("/api/results")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(getResponseJson("get-all-results.json")));
    }

    @Test
    public void should_get_results_filtered_by_type_download() throws Exception {
        mvc.perform(post("/api/results")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(new Filters(SpeedTestService.TEST_TYPE.DOWNLOAD, null, null))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(getResponseJson("get-results-filtered-by-type-download.json")));
    }

    @Test
    public void should_get_results_filtered_by_type_upload() throws Exception {
        mvc.perform(post("/api/results")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(new Filters(SpeedTestService.TEST_TYPE.UPLOAD, null, null))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(getResponseJson("get-results-filtered-by-type-upload.json")));
    }

    @Test
    public void should_get_results_filtered_by_from_date() throws Exception {
        mvc.perform(post("/api/results")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(new Filters(null, Date.from(LocalDate.of(2017, 6, 7).atStartOfDay(ZoneId.systemDefault()).toInstant()), null))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(getResponseJson("get-results-filtered-by-from-date.json")));
    }

    @Test
    public void should_get_results_filtered_by_to_date() throws Exception {
        mvc.perform(post("/api/results")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(new Filters(null, null, Date.from(LocalDate.of(2017, 6, 2).atStartOfDay(ZoneId.systemDefault()).toInstant())))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(getResponseJson("get-results-filtered-by-to-date.json")));
    }

    private String asJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    private String getResponseJson(final String fileName) throws IOException {
        return FileUtils.readFileToString(new File("src/test/resources/responses/" + fileName), "UTF-8");
    }

}
