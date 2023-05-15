package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entities.Congestion;

@Repository
public interface CongestionRepository extends JpaRepository<Congestion, Long>{

    @Query("SELECT c FROM Congestion c WHERE resolve=false")
    public Iterable<Congestion> getCongestionByResolve();
    
}
