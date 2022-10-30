package com.baywa.power.api;

import com.baywa.power.persistence.models.PowerData;
import com.baywa.power.persistence.models.PowerDataDTO;
import com.baywa.power.service.PowerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/power-data")
public class WindPowerController {

    private final PowerService powerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PowerData> getAllPowerDataPoints() {
        log.debug("Getting all power data");
        return powerService.getAllPowerDataPoints();
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePowerProducedValue(@PathVariable String id, @Valid @RequestBody PowerDataDTO powerDataDTO) {
        log.debug("Correcting power produced value of data point with id: " + id);
        powerService.updatePowerDataPoint(id, powerDataDTO);
    }
}
