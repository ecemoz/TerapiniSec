package com.yildiz.terapinisec.repository;

import com.yildiz.terapinisec.model.MedidationSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedidationSessionRepository  extends JpaRepository<MedidationSession, Long> {

}
