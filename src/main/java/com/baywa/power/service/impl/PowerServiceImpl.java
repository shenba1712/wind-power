package com.baywa.power.service.impl;

import com.baywa.power.persistence.models.PowerData;
import com.baywa.power.persistence.PowerDataRepository;
import com.baywa.power.persistence.models.PowerDataDTO;
import com.baywa.power.service.PowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PowerServiceImpl implements PowerService {

    private final PowerDataRepository repository;

    @Override
    public void save(PowerData powerData) {
        Assert.notNull(powerData, "Power Data must not be null");
        repository.saveAndFlush(powerData);
    }

    @Override
    public void updatePowerDataPoint(String id, PowerDataDTO updatedData) {
        Assert.notNull(id, "Id must not be null");
        Assert.notNull(updatedData, "Updated data object must not be null");
        Assert.notNull(updatedData.getCorrectedPowerProduced(), "Updated value must not be null");
        PowerData powerData = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No power data found with id: " + id));
        powerData.setPowerProduced(updatedData.getCorrectedPowerProduced());
        save(powerData);
    }

    @Override
    public List<PowerData> getAllPowerDataPoints() {
        return repository.findAll();
    }
}
