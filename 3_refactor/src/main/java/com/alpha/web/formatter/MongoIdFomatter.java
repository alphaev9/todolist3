package com.alpha.web.formatter;

import com.alpha.repository.PersistId;
import com.alpha.repository.mongodb.MongoID;
import org.bson.types.ObjectId;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class MongoIdFomatter implements Formatter<PersistId> {
    @Override
    public PersistId parse(String s, Locale locale) {
        return new MongoID(new ObjectId(s));
    }

    @Override
    public String print(PersistId persistId, Locale locale) {
        return persistId.getRealId();
    }
}
