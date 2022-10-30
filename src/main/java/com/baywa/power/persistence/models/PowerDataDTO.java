package com.baywa.power.persistence.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PowerDataDTO {
    @NotNull
    private Double correctedPowerProduced;
}
