package com.nsdl.telemedicine.slot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.slot.entity.AudDrSlotMstr;
@Repository
public interface AudDrSlotMstrRepository extends JpaRepository<AudDrSlotMstr, Integer> {

}
