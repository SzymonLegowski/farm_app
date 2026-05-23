package com.farmapp.rest.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.farmapp.rest.entity.Sow;
import com.farmapp.rest.enums.SowStatus;

@Repository
public interface SowRepository extends JpaRepository<Sow, Integer>{
    List<Sow> findByStatusIsIn(Set<SowStatus> statuses);

    @Query(
        nativeQuery = true,
        value = """
                    SELECT s.group_nr
                    FROM litter l 
                    JOIN sow s ON s.id = l.sow_id
                    ORDER BY (jsonb_array_elements(l.inseminations) ->>'date')::date
                    DESC LIMIT 1;
                """

    )
    Integer getLatestSowGroup();

   }