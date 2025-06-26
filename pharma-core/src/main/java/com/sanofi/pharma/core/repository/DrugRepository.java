package com.sanofi.pharma.core.repository;

import com.sanofi.pharma.core.entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {

    @Query("from Drug e where e.id in (:drugIdList) and e.isDeleted =:isDeleted ")
    List<Drug> findByIdIn(@Param("drugIdList") List<Long> drugIdList, @Param("isDeleted") Boolean isDeleted);
}
