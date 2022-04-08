package com.springbatch.repository;

import com.springbatch.model.StoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreHistoryRepository extends JpaRepository<StoreHistory, Long> {
}
