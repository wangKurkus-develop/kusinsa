package com.kurkus.kusinsa.repository.mongo;

import com.kurkus.kusinsa.entity.documents.ClickRank;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClickRankRepository extends MongoRepository<ClickRank, String> {

}
