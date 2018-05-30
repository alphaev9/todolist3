package com.alpha.repository.mongodb.codec;

import com.alpha.repository.entity.Cooperator;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class CooperatorCodec implements Codec<Cooperator> {
    @Override
    public Cooperator decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        bsonReader.readName();
        String name = bsonReader.readString();
        bsonReader.readName();
        String email = bsonReader.readString();
        bsonReader.readEndDocument();
        Cooperator cooperator = new Cooperator();
        cooperator.setName(name);
        cooperator.setEmail(email);
        return cooperator;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Cooperator cooperator, EncoderContext encoderContext) {
        if (cooperator == null) {
            bsonWriter.writeNull();
        } else {
            bsonWriter.writeStartDocument();
            bsonWriter.writeName("name");
            bsonWriter.writeString(cooperator.getName());
            bsonWriter.writeName("email");
            bsonWriter.writeString(cooperator.getEmail());
            bsonWriter.writeEndDocument();
        }
    }

    @Override
    public Class<Cooperator> getEncoderClass() {
        return Cooperator.class;
    }
}
