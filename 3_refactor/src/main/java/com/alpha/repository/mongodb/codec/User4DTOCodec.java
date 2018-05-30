package com.alpha.repository.mongodb.codec;

import com.alpha.repository.mongodb.dto.UserDTO;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class User4DTOCodec implements Codec<UserDTO> {
    @Override
    public UserDTO decode(BsonReader bsonReader, DecoderContext decoderContext) {
        UserDTO user4DTO = new UserDTO();
        bsonReader.readStartDocument();
        ObjectId objectId = bsonReader.readObjectId();
        user4DTO.setId(objectId);
        bsonReader.readName();
        bsonReader.readStartDocument();
        String name = bsonReader.readString();
        user4DTO.setName(name);
        bsonReader.readName();
        String password = bsonReader.readString();
        user4DTO.setPassword(password);

        user4DTO.setObjectIdsOfBacklog(new ArrayList<>());
        bsonReader.readName();
        System.out.println("1:  " + bsonReader.getCurrentBsonType());
        if (bsonReader.getCurrentBsonType() == BsonType.NULL) {
            bsonReader.readNull();
        } else {
            bsonReader.readStartArray();
            ObjectId backlogObjectId;
            /******************************
             * mongodb API 设计太费解
             * */
            while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                if(bsonReader.getCurrentBsonType()==BsonType.OBJECT_ID){
                    backlogObjectId = bsonReader.readObjectId();
                    user4DTO.getObjectIdsOfBacklog().add(backlogObjectId);
                }
            }
            bsonReader.readEndArray();
        }

        bsonReader.readEndDocument();
        bsonReader.readEndDocument();
        return user4DTO;
    }

    @Override
    public void encode(BsonWriter bsonWriter, UserDTO user4DTO, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();

        bsonWriter.writeName("name");
        bsonWriter.writeString(user4DTO.getName());
        bsonWriter.writeName("password");
        bsonWriter.writeString(user4DTO.getPassword());

        bsonWriter.writeName("backlogs");
        if (user4DTO.getObjectIdsOfBacklog() == null) {
            bsonWriter.writeNull();
        } else {
            bsonWriter.writeStartArray();
            user4DTO.getObjectIdsOfBacklog().forEach(
                    objectId -> {
                        bsonWriter.writeObjectId(objectId);
                    }
            );
            bsonWriter.writeEndArray();
        }
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<UserDTO> getEncoderClass() {
        return UserDTO.class;
    }
}
