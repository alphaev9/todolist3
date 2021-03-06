package com.alpha.web.formatter;

import com.alpha.repository.PersistId;
import com.alpha.repository.rdb.singleTable.RdbId;
import org.springframework.format.Formatter;

import java.util.Locale;

public class RdbIdFomatter implements Formatter<PersistId> {
    @Override
    public PersistId parse(String s, Locale locale) {
        return new RdbId(Integer.valueOf(s));
    }

    @Override
    public String print(PersistId persistId, Locale locale) {
        return persistId.getRealId();
    }
}
