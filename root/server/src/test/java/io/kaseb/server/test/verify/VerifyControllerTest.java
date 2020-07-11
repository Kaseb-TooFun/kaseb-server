//package io.kaseb.server.test.verify;
//
//import io.kaseb.server.verify.controller.VerifyController;
//import io.kaseb.server.verify.response.VerifyResponseDto;
//import io.kaseb.server.verify.service.VerifyService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// * @author Seyyed Mahdiyar Zerehpoush
// */
//@WebMvcTest(VerifyController.class)
//public class VerifyControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private VerifyService verifyService;
//
//    @Test
//    public void verify() throws Exception {
//        VerifyResponseDto okResponse = new VerifyResponseDto(true);
//        when(verifyService.verify()).thenReturn(okResponse);
//        this.mockMvc
//                .perform(get("/api/v1/verify"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("\"up\":true")));
//    }
//}