public class DSL {

        @Support
        public static <T> Field<T> field(Select<? extends Record1<T>> select) {
            return select.<T>asField();
        }

        @Support
        public static <T> Field<T> coalesce(T value, T... values) {
            return coalesce(Utils.field(value), Utils.fields(values).toArray(new Field[0]));
        }

        @Support
        public static <T> Field<T> coalesce(Field<T> field, T value) {
            return coalesce(field, Utils.field(value, field));
        }

        @Support
        public static <T> Field<T> coalesce(Field<T> field, Field<?>... fields) {
            return new Coalesce<T>(nullSafeDataType(field), nullSafe(combine(field, fields)));
        }

        protected static Field<?>[] nullSafe(Field<?>... fields) {
            if (fields == null)
                return new Field[0];
            Field<?>[] result = new Field<?>[fields.length];
            for (int i = 0; i < fields.length; i++) {
                result[i] = nullSafe(fields[i]);
            }
            return result;
        }

        protected static <T> DataType<T> nullSafeDataType(Field<T> field) {
            return (DataType<T>) (field == null ? SQLDataType.OTHER : field.getDataType());
        }

        @Support
        public static <T> DataType<T> getDataType(Class<T> type) {
            return DefaultDataType.getDataType(SQLDialect.DEFAULT, type);
        }

}
