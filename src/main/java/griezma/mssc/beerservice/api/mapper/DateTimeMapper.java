package griezma.mssc.beerservice.api.mapper;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateTimeMapper {
    public Timestamp asTimestamp(OffsetDateTime dt) {
        if (dt == null) return null;
        return Timestamp.valueOf(dt.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

    public OffsetDateTime asOffsetDateTime(Timestamp ts) {
        if (ts == null) return null;
        return OffsetDateTime.of(ts.toLocalDateTime(), ZoneOffset.UTC);
    }
}
