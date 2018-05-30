package com.alpha.repository.mongodb.codec;

import com.alpha.repository.entity.Backlog;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class BacklogCodecProvider implements CodecProvider {
    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if(aClass== Backlog.class){
            return (Codec<T>) new BacklogCodec(codecRegistry);
        }
        return null;
    }
}
