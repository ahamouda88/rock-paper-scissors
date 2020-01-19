package com.challenge.game.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.challenge.game.application.MainApplication;
import com.challenge.game.model.GameResult;
import com.challenge.game.model.Hand;
import com.challenge.game.rest.constant.PathConstant;
import com.challenge.game.rest.request.GameMoveRequest;
import com.challenge.game.utils.GameMoveTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class GameControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private GameController controller;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                                 .setControllerAdvice(new ExceptionController())
                                 .build();
    }

    @Test
    public void testHandleUserGameMove_givenValidMove_returnValidResult() throws Exception {
        GameMoveRequest request = GameMoveTestUtils.createRequest(null, Hand.ROCK);

        mockMvc.perform(post(PathConstant.MAIN_PATH).contentType(APPLICATION_JSON)
                                                    .content(mapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.result", notNullValue()))
               .andExpect(jsonPath("$.computerHand", notNullValue()))
               .andExpect(jsonPath("$.score", notNullValue()))
               .andExpect(jsonPath("$.score.humanPoints", notNullValue()))
               .andExpect(jsonPath("$.score.machinePoints", notNullValue()));
    }

    @Test
    public void testHandleUserGameMove_givenMissingHand_returnErrorMessage() throws Exception {
        GameMoveRequest request = new GameMoveRequest();

        mockMvc.perform(post(PathConstant.MAIN_PATH).contentType(APPLICATION_JSON)
                                                    .content(mapper.writeValueAsString(request)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.errorMessage", equalTo("User input is mandatory")))
               .andExpect(jsonPath("$.status", equalTo(BAD_REQUEST.name())));
    }

    @Test
    public void testHandleUserGameMove_givenInvalidMove_returnUnknownHandResult() throws Exception {
        GameMoveRequest request = GameMoveTestUtils.createRequest(null, "invalid");

        mockMvc.perform(post(PathConstant.MAIN_PATH).contentType(APPLICATION_JSON)
                                                    .content(mapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.result", equalTo(GameResult.UNKNOWN.name())));
    }

}
