package com.fc.housebatch.core.repository;

import com.fc.housebatch.core.entity.Apt;
import com.fc.housebatch.core.entity.AptDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AptDealRepository extends JpaRepository<AptDeal, Long> {

    Optional<AptDeal> findAptDealByAptAndExclusiveAreaAndDealDateAndDealAmountAndFloor(
            Apt apt, Double exclusiveArea, LocalDate dealDate, Long dealAmount, Integer floor);

    List<AptDeal> findByDealCanceledIsFalseAndDealDateEquals(LocalDate dealDate);
}
