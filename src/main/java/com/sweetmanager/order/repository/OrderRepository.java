package com.sweetmanager.order.repository;

import com.sweetmanager.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsByProductId(Long id);

    List<Order> findByCreatedBy(String email);

    @Query("""
        SELECT 
            FUNCTION('date', o.createdAt) AS day,
            COUNT(o.id) AS totalSales,
            SUM(o.total) AS totalValue
        FROM Order o
        WHERE o.status = 'CONCLUIDO'
        GROUP BY FUNCTION('date', o.createdAt)
        ORDER BY FUNCTION('date', o.createdAt)
    """)
    List<Object[]> getDailySales();
}
