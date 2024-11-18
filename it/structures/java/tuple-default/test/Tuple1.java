package test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * Tuple containing primitive.
 */
@JsonSerialize(using = Tuple1.Serializer.class)
@JsonDeserialize(using = Tuple1.Deserializer.class)
public class Tuple1 {
    @JsonProperty("a")
    final String a;
    @JsonProperty("b")
    final long b;

    @JsonCreator
    public Tuple1(
        @JsonProperty("a") String a,
        @JsonProperty("b") long b
    ) {
        Objects.requireNonNull(a, "a: must not be null");
        this.a = a;
        this.b = b;
    }

    @JsonProperty("a")
    public String getA() {
        return this.a;
    }

    @JsonProperty("b")
    public long getB() {
        return this.b;
    }

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder();

        b.append("Tuple1(");
        b.append("a=");
        b.append(this.a.toString());
        b.append(", ");
        b.append("b=");
        b.append(Long.toString(this.b));
        b.append(")");

        return b.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        final StringBuilder b = new StringBuilder();
        result = result * 31 + this.a.hashCode();
        result = result * 31 + Long.valueOf(this.b).hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object other_) {
        if (other_ == null) {
            return false;
        }

        if (!(other_ instanceof Tuple1)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        final Tuple1 o_ = (Tuple1)other_;

        if (!this.a.equals(o_.a)) {
            return false;
        }

        if (this.b != o_.b) {
            return false;
        }

        return true;
    }

    public static class Builder {
        private Optional<String> a;
        private Optional<Long> b;

        private Builder() {
            this.a = Optional.empty();
            this.b = Optional.empty();
        }

        public Tuple1 build() {
            final String a = this.a
                .orElseThrow(() -> new RuntimeException("a: missing required value"));
            final long b = this.b
                .orElseThrow(() -> new RuntimeException("b: missing required value"));

            return new Tuple1(
                a,
                b
            );
        }

        public Builder a(final String a) {
            this.a = Optional.of(a);
            return this;
        }

        public Builder b(final long b) {
            this.b = Optional.of(b);
            return this;
        }
    }

    /**
     * Construct a new builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Serializer extends JsonSerializer<Tuple1> {
        @Override
        public void serialize(final Tuple1 value_, final JsonGenerator gen_, final SerializerProvider provider_) throws IOException {
            gen_.writeStartArray();

            gen_.writeString(value_.a);

            gen_.writeNumber(value_.b);

            gen_.writeEndArray();
        }
    }

    public static class Deserializer extends JsonDeserializer<Tuple1> {
        @Override
        public Tuple1 deserialize(final JsonParser parser_, final DeserializationContext ctxt_) throws IOException {
            if (parser_.getCurrentToken() != JsonToken.START_ARRAY) {
                throw ctxt_.wrongTokenException(parser_, JsonToken.START_ARRAY, null);
            }

            if (parser_.nextToken() != JsonToken.VALUE_STRING) {
                throw ctxt_.wrongTokenException(parser_, JsonToken.VALUE_STRING, null);
            }

            final String a = parser_.getText();

            if (parser_.nextToken() != JsonToken.VALUE_NUMBER_INT) {
                throw ctxt_.wrongTokenException(parser_, JsonToken.VALUE_NUMBER_INT, null);
            }

            final long b = parser_.getLongValue();

            if (parser_.nextToken() != JsonToken.END_ARRAY) {
                throw ctxt_.wrongTokenException(parser_, JsonToken.END_ARRAY, null);
            }

            return new Tuple1(a, b);
        }
    }
}
