package com.baywa.power.service;

import com.baywa.power.TestUtils;
import com.baywa.power.persistence.PowerDataRepository;
import com.baywa.power.persistence.models.PowerData;
import com.baywa.power.persistence.models.PowerDataDTO;
import com.baywa.power.persistence.models.WindPark;
import com.baywa.power.service.impl.PowerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class PowerServiceTest {

    private PowerDataRepository powerDataRepository;
    private PowerServiceImpl powerService;

    @BeforeEach
    public void setUp() {
        powerDataRepository = mock(PowerDataRepository.class);
        powerService = new PowerServiceImpl(powerDataRepository);
    }

    @Nested
    public class SavePowerDataPoint {
        @Test
        public void failOnSavingNullObjectTest() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> powerService.save(null));
            verify(powerDataRepository, never()).saveAndFlush(any(PowerData.class));
        }

        @Test
        public void savingPowerDataSuccessTest() {
            PowerData powerData = TestUtils.createPowerData("a1-b2-c3", WindPark.LIM, 2.33);
            powerService.save(powerData);
            verify(powerDataRepository).saveAndFlush(powerData);
        }
    }

    @Test
    public void getAllDataTest() {
        powerService.getAllPowerDataPoints();
        verify(powerDataRepository).findAll();
    }

    @Nested
    public class UpdatePowerDataTest {
        @Test
        public void nullId() {
            PowerDataDTO updateObject = new PowerDataDTO();
            updateObject.setCorrectedPowerProduced(2.5);

            Assertions.assertThrows(IllegalArgumentException.class, () ->powerService.updatePowerDataPoint(null, updateObject));

            verify(powerDataRepository, never()).findById(anyString());
            verify(powerDataRepository, never()).saveAndFlush(any(PowerData.class));
        }

        @Test
        public void nullUpdateObject() {
            Assertions.assertThrows(IllegalArgumentException.class, () ->powerService.updatePowerDataPoint("a1-b2-c3", null));

            verify(powerDataRepository, never()).findById(anyString());
            verify(powerDataRepository, never()).saveAndFlush(any(PowerData.class));
        }

        @Test
        public void nullPowerValue() {
            PowerDataDTO updateObject = new PowerDataDTO();
            updateObject.setCorrectedPowerProduced(null);

            Assertions.assertThrows(IllegalArgumentException.class, () ->powerService.updatePowerDataPoint("a1-b2-c3", updateObject));

            verify(powerDataRepository, never()).findById(anyString());
            verify(powerDataRepository, never()).saveAndFlush(any(PowerData.class));
        }

        @Test
        public void powerDataPointNotFound() {
            PowerDataDTO updateObject = new PowerDataDTO();
            updateObject.setCorrectedPowerProduced(2.2);

            when(powerDataRepository.findById(anyString())).thenReturn(Optional.empty());

            Assertions.assertThrows(NoSuchElementException.class, () ->powerService.updatePowerDataPoint("a1-b2-c3", updateObject));

            verify(powerDataRepository, never()).saveAndFlush(any(PowerData.class));
        }

        @Test
        public void success() {
            PowerDataDTO updateObject = new PowerDataDTO();
            updateObject.setCorrectedPowerProduced(2.2);

            PowerData powerData = TestUtils.createPowerData("a1-b2-c3", WindPark.QMA, 3.7);

            when(powerDataRepository.findById(anyString())).thenReturn(Optional.of(powerData));

            powerService.updatePowerDataPoint("a1-b2-c3", updateObject);

            ArgumentCaptor<PowerData> captor = ArgumentCaptor.forClass(PowerData.class);
            verify(powerDataRepository).saveAndFlush(captor.capture());

            PowerData actualData = captor.getValue();
            Assertions.assertEquals(updateObject.getCorrectedPowerProduced(), actualData.getPowerProduced());
        }
    }
}
