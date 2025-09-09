package com.example.tradestore.service.impl;

import com.example.tradestore.model.OrderSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqName) {
        OrderSequence counter = mongoOperations.findAndModify(query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                OrderSequence.class);
        return !java.util.Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}