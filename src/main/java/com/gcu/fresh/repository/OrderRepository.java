package com.gcu.fresh.repository;

import org.springframework.data.repository.CrudRepository;
import com.gcu.fresh.models.OrderTransferModel;

public interface OrderRepository extends CrudRepository<OrderTransferModel, Long> {

}
