package com.alpha.web.formatter;

import com.alpha.repository.rdb.singleTable.RdbId;
import org.springframework.format.Formatter;

import java.util.Locale;

public class RdbIdFomatter implements Formatter<RdbId> {
    @Override
    public RdbId parse(String s, Locale locale) {
        return new RdbId(Integer.valueOf(s));
    }

    @Override
    public String print(RdbId rdbId, Locale locale) {
        return rdbId.getRealId().toString();
    }
}
