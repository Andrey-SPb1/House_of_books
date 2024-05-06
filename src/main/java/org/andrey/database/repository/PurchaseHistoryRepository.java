package org.andrey.database.repository;

import org.andrey.database.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Integer> {

    @Query("select ph from PurchaseHistory ph " +
            "join fetch ph.book " +
            "where ph.user.email = :email ")
    List<PurchaseHistory> findByUserEmail(String email);

}
