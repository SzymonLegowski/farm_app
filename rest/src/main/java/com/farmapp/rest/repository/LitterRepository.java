package com.farmapp.rest.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.farmapp.rest.entity.Litter;



@Repository
public interface LitterRepository extends JpaRepository<Litter, Long> {
    List<Litter> findBySowId(Long sowId);
    // @Query("""
    //         SELECT DISTINCT l FROM Litter l
    //         WHERE l.farrowing BETWEEN :start AND :end
    //         OR l.weaning BETWEEN :start AND :end
    //         """)
    @Query(value =
        """
            SELECT DISTINCT ON (l.id) l.*
            FROM Litter l
            JOIN insemination_litter il ON l.id = il.litter_id
            JOIN Insemination i ON i.id = il.insemination_id
            WHERE l.farrowing BETWEEN :start AND :end
            OR l.weaning BETWEEN :start AND :end
            OR i.date + 114 BETWEEN :start AND :end
            ORDER BY l.id, i.id DESC
        """, nativeQuery = true
    )
    List<Litter> findLittersInPeriod(
        @Param("start")LocalDate start, 
        @Param("end") LocalDate end
    );
}