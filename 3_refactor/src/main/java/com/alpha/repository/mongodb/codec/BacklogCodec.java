package com.alpha.repository.mongodb.codec;

import com.alpha.repository.entity.Address;
import com.alpha.repository.entity.Backlog;
import com.alpha.repository.entity.BacklogState;
import com.alpha.repository.entity.Cooperator;
import com.alpha.repository.mongodb.MongoID;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

import java.sql.Date;
import java.util.ArrayList;

public class BacklogCodec implements Codec<Backlog> {
    private final CodecRegistry codecRegistry;

    public BacklogCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public Backlog decode(BsonReader bsonReader, DecoderContext decoderContext) {
        System.out.println("backlog:decode*********");
        Codec<BacklogState> backlogStateCodec = codecRegistry.get(BacklogState.class);
        Codec<Address> addressCodec = codecRegistry.get(Address.class);
        Codec<Cooperator> cooperatorCodec = codecRegistry.get(Cooperator.class);

        Backlog backlog = new Backlog();

        bsonReader.readStartDocument();
        ObjectId objectId = bsonReader.readObjectId();
        backlog.setId(new MongoID(objectId));

        bsonReader.readName();
        bsonReader.readStartDocument();
        bsonReader.readName();
        String title = bsonReader.readString();
        backlog.setTitle(title);
        bsonReader.readName();
        String description = bsonReader.readString();
        backlog.setDescription(description);
        bsonReader.readName();
        String path = null;
        if (bsonReader.getCurrentBsonType() == BsonType.NULL) {
            bsonReader.readNull();
        } else {
            path = bsonReader.readString();
        }
        backlog.setAttachment(path);
        bsonReader.readName();
        long dueTime = bsonReader.readDateTime();
        backlog.setDueTime(new Date(dueTime));
        bsonReader.readName();
        BacklogState backlogState = backlogStateCodec.decode(bsonReader, decoderContext);
        backlog.setState(backlogState);
        bsonReader.readName();
        Address address = addressCodec.decode(bsonReader, decoderContext);
        backlog.setAddress(address);

        bsonReader.readName();
        backlog.setCooperators(new ArrayList<>());
        bsonReader.readStartArray();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            Cooperator cooperator = cooperatorCodec.decode(bsonReader, decoderContext);
            backlog.getCooperators().add(cooperator);
        }

        bsonReader.readEndArray();
        bsonReader.readEndDocument();
        bsonReader.readEndDocument();
        return backlog;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Backlog backlog, EncoderContext encoderContext) {
        System.out.println("backlog:encode*********");
        Codec<BacklogState> backlogStateCodec = codecRegistry.get(BacklogState.class);
        Codec<Address> addressCodec = codecRegistry.get(Address.class);
        Codec<Cooperator> cooperatorCodec = codecRegistry.get(Cooperator.class);

        bsonWriter.writeStartDocument();
        bsonWriter.writeString("title", backlog.getTitle());
        bsonWriter.writeString("description", backlog.getDescription());
        if (backlog.getAttachment() == null) {
            bsonWriter.writeNull("attachment");
        } else {
            bsonWriter.writeString("attachment", backlog.getAttachment());
        }
        bsonWriter.writeName("dueTime");
        bsonWriter.writeDateTime(backlog.getDueTime().getTime());
        bsonWriter.writeName("state");
        encoderContext.encodeWithChildContext(backlogStateCodec, bsonWriter, backlog.getState());

        bsonWriter.writeName("address");
        encoderContext.encodeWithChildContext(addressCodec, bsonWriter, backlog.getAddress());

        bsonWriter.writeName("cooperators");
        bsonWriter.writeStartArray();
        backlog.getCooperators().forEach(
                cooperator -> {
                    encoderContext.encodeWithChildContext(cooperatorCodec, bsonWriter, cooperator);

//                    bsonWriter.writeName("name");
//                    bsonWriter.writeString(cooperator.getName());
//                    bsonWriter.writeName("email");
//                    bsonWriter.writeString(cooperator.getEmail());
                }
        );
        bsonWriter.writeEndArray();
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Backlog> getEncoderClass() {
        return Backlog.class;
    }
}
