package com.alpha.repository.mongodb.codec;

import com.alpha.repository.entity.BacklogState;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class BacklogStateCodec implements Codec<BacklogState> {
    @Override
    public BacklogState decode(BsonReader bsonReader, DecoderContext decoderContext) {
        String state = bsonReader.readString();
        System.out.println("state decode");
        return BacklogState.valueOf(state);
    }

    @Override
    public void encode(BsonWriter bsonWriter, BacklogState state, EncoderContext encoderContext) {
        bsonWriter.writeString(state.toString());
        System.out.println("state  encode");
    }

    @Override
    public Class<BacklogState> getEncoderClass() {
        return BacklogState.class;
    }
}
