package com.baywa.power.persistence;

import com.baywa.power.persistence.models.PowerData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PowerDataRepository extends JpaRepository<PowerData, String> {

}
