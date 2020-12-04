package griezma.mssc.beerservice.data.mapper;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateTimeMapper {
    public Timestamp toTimestamp(OffsetDateTime dt) {
        if (dt == null) return null;
        return Timestamp.valueOf(dt.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

    public OffsetDateTime toDateTime(Timestamp ts) {
        if (ts == null) return null;
        return OffsetDateTime.ofInstant(ts.toInstant(), ZoneOffset.UTC);
    }
}
