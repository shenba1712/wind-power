package com.baywa.power.api;

import com.baywa.power.TestUtils;
import com.baywa.power.persistence.models.PowerData;
import com.baywa.power.persistence.models.PowerDataDTO;
import com.baywa.power.persistence.models.WindPark;
import com.baywa.power.service.PowerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WindPowerController.class)
class WindPowerControllerTest {

    @MockBean
    private PowerService powerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllPowerDataPoints() throws Exception {
        PowerData powerData1 = TestUtils.createPowerData("a1-b2-c3", WindPark.DKX, 5.69);
        PowerData powerData2 = TestUtils.createPowerData("d4-e5-f6", WindPark.LIM, 3.89);
        PowerData powerData3 = TestUtils.createPowerData("g7-h8-i9", WindPark.PPA, 7.88);

        when(powerService.getAllPowerDataPoints()).thenReturn(List.of(powerData1, powerData2, powerData3));

        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/power-data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].id", Matchers.is(powerData1.getId())))
                .andExpect(jsonPath("$[0].powerProduced", Matchers.is(powerData1.getPowerProduced())))
                .andExpect(jsonPath("$[0].windPark", Matchers.is(powerData1.getWindPark().name())))
                .andExpect(jsonPath("$[0].timestamp", Matchers.is(powerData1.getTimestamp().format(pattern))))
                .andExpect(jsonPath("$[0].createdOn", Matchers.is(powerData1.getCreatedOn().format(pattern))))
                .andExpect(jsonPath("$[0].updatedOn", Matchers.is(powerData1.getUpdatedOn().format(pattern))))

                .andExpect(jsonPath("$[1].id", Matchers.is(powerData2.getId())))
                .andExpect(jsonPath("$[1].powerProduced", Matchers.is(powerData2.getPowerProduced())))
                .andExpect(jsonPath("$[1].windPark", Matchers.is(powerData2.getWindPark().name())))
                .andExpect(jsonPath("$[1].timestamp", Matchers.is(powerData2.getTimestamp().format(pattern))))
                .andExpect(jsonPath("$[1].createdOn", Matchers.is(powerData2.getCreatedOn().format(pattern))))
                .andExpect(jsonPath("$[1].updatedOn", Matchers.is(powerData2.getUpdatedOn().format(pattern))))

                .andExpect(jsonPath("$[2].id", Matchers.is(powerData3.getId())))
                .andExpect(jsonPath("$[2].powerProduced", Matchers.is(powerData3.getPowerProduced())))
                .andExpect(jsonPath("$[2].windPark", Matchers.is(powerData3.getWindPark().name())))
                .andExpect(jsonPath("$[2].timestamp", Matchers.is(powerData3.getTimestamp().format(pattern))))
                .andExpect(jsonPath("$[2].createdOn", Matchers.is(powerData3.getCreatedOn().format(pattern))))
                .andExpect(jsonPath("$[2].updatedOn", Matchers.is(powerData3.getUpdatedOn().format(pattern))));
    }

    @Nested
    public class UpdatePowerProducedTest {
        @Test
        public void nullData() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/power-data/a1-b2-c3")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(null)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        public void dataNotFound() throws Exception {
            PowerDataDTO update = new PowerDataDTO();
            update.setCorrectedPowerProduced(5.67);
            doThrow(new NoSuchElementException()).when(powerService).updatePowerDataPoint("random-id", update);
            mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/power-data/random-id")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(update)))
                    .andExpect(status().isNotFound());
        }

        @Test
        public void success() throws Exception {
            PowerDataDTO update = new PowerDataDTO();
            update.setCorrectedPowerProduced(5.67);
            doNothing().when(powerService).updatePowerDataPoint(anyString(), eq(update));
            mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/power-data/id")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(update)))
                    .andExpect(status().isNoContent());
        }
    }
}
