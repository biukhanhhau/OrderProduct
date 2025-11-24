package org.biukhanhhau.storedproduct.Repository;

import org.biukhanhhau.storedproduct.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
}
