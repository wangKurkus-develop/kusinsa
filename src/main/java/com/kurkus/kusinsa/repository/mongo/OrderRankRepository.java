package com.kurkus.kusinsa.repository.mongo;

import com.kurkus.kusinsa.entity.documents.OrderRank;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRankRepository extends MongoRepository<OrderRank, String> {
}
