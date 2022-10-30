package com.baywa.power.service;

import com.baywa.power.persistence.models.PowerData;
import com.baywa.power.persistence.models.PowerDataDTO;

import java.util.List;

public interface PowerService {

    void save(PowerData powerData);

    void updatePowerDataPoint(String id, PowerDataDTO updatedData);

    List<PowerData> getAllPowerDataPoints();
}
