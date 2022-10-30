package com.baywa.power.utils;

import com.baywa.power.config.LocalDateTimeTypeAdapter;
import com.baywa.power.persistence.models.PowerData;
import com.baywa.power.service.impl.PowerServiceImpl;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class TestData {

    private final PowerServiceImpl powerService;

    @PostConstruct
    public void insertTestData() {
        insertTestPowerData();
    }

    private void insertTestPowerData() {
        log.debug("Inserting test power data");

        try (BufferedReader reader = new BufferedReader(new
                InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("powerProduced.json"))))) {

            // Convert JSON file to Powerdata objects using GSON
            GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter());
            List<PowerData> powerDataList = gsonBuilder.setPrettyPrinting().create().fromJson(reader, new TypeToken<List<PowerData>>() {}.getType());

            powerDataList.forEach(powerService::save);

        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while saving test data", e);
        }
    }
}

