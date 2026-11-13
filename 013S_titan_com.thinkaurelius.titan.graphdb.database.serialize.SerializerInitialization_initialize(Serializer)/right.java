// class name : com.thinkaurelius.titan.graphdb.database.serialize.SerializerInitialization
// method name: initialize(Serializer)
// side       : right
// region     : lines 31-57 (affected lines: [31, 52, 53, 54, 55, 57])
class SerializerInitialization {
    void initialize_Serializer_() {
        serializer.registerClass(double[].class, KRYO_OFFSET + 10);
        serializer.registerClass(long[].class, KRYO_OFFSET + 11);
        serializer.registerClass(byte[].class, new ByteArrayHandler(), KRYO_OFFSET + 12);
        serializer.registerClass(boolean[].class, KRYO_OFFSET + 13);
        serializer.registerClass(IndexType.class, KRYO_OFFSET + 14);
        serializer.registerClass(TitanTypeClass.class, KRYO_OFFSET + 15);
        serializer.registerClass(Integer.class, new IntegerSerializer(), KRYO_OFFSET + 16);
        serializer.registerClass(Double.class, new DoubleSerializer(), KRYO_OFFSET + 17);
        serializer.registerClass(Float.class, new FloatSerializer(), KRYO_OFFSET + 18);
        serializer.registerClass(Long.class, new LongSerializer(), KRYO_OFFSET + 19);
        serializer.registerClass(IndexType.class, KRYO_OFFSET + 20);
        serializer.registerClass(IndexType[].class, KRYO_OFFSET + 21);
        serializer.registerClass(Geoshape.class, new GeoshapeHandler(), KRYO_OFFSET + 22);
        serializer.registerClass(Byte.class, new ByteSerializer(), KRYO_OFFSET + 23);
        serializer.registerClass(Short.class, new ShortSerializer(), KRYO_OFFSET + 24);
        serializer.registerClass(Character.class, new CharacterSerializer(), KRYO_OFFSET + 25);
        serializer.registerClass(Boolean.class, new BooleanSerializer(), KRYO_OFFSET + 26);
        serializer.registerClass(Object.class, KRYO_OFFSET + 27);
        serializer.registerClass(FullFloat.class, new FullFloatHandler(), KRYO_OFFSET + 28);
        serializer.registerClass(FullDouble.class, new FullDoubleHandler(), KRYO_OFFSET + 29);
        serializer.registerClass(char[].class, KRYO_OFFSET + 30);
        serializer.registerClass(short[].class, KRYO_OFFSET + 31);
        serializer.registerClass(float[].class, KRYO_OFFSET + 32);

        Preconditions.checkArgument(KRYO_OFFSET + 29 < RESERVED_ID_OFFSET, "ID allocation overflow!");
    }

    }
}
