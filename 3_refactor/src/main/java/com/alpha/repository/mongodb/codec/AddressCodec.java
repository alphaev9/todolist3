package com.alpha.repository.mongodb.codec;

import com.alpha.repository.entity.Address;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class AddressCodec implements Codec<Address> {
    @Override
    public Address decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        bsonReader.readName();
        String street = bsonReader.readString();
        bsonReader.readName();
        int number = bsonReader.readInt32();
        bsonReader.readEndDocument();
        Address address = new Address();
        address.setStreet(street);
        address.setNumber(number);
        return address;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Address address, EncoderContext encoderContext) {
        if (address == null) {
            bsonWriter.writeNull();
        } else {
            bsonWriter.writeStartDocument();
            bsonWriter.writeName("street");
            bsonWriter.writeString(address.getStreet());
            bsonWriter.writeName("number");
            bsonWriter.writeInt32(address.getNumber());
            bsonWriter.writeEndDocument();
        }
    }

    @Override
    public Class<Address> getEncoderClass() {
        return Address.class;
    }
}
