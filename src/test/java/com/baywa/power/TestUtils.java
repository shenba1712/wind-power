package com.baywa.power;

import com.baywa.power.persistence.models.PowerData;
import com.baywa.power.persistence.models.WindPark;

import java.time.LocalDateTime;

public class TestUtils {

    public static PowerData createPowerData(String id, WindPark windPark, Double powerProduced) {
        PowerData powerData = new PowerData();
        powerData.setId(id);
        powerData.setPowerProduced(powerProduced);
        powerData.setPeriod("30m");
        powerData.setTimestamp(LocalDateTime.now().minusDays(30));
        powerData.setWindPark(windPark);
        powerData.setCreatedOn(LocalDateTime.now());
        powerData.setUpdatedOn(LocalDateTime.now());

        return powerData;
    }
}
