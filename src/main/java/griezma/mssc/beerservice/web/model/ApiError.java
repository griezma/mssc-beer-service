package griezma.mssc.beerservice.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

import static griezma.mssc.beerservice.web.model.Formats.ISO_DATETIME;

@Data
@AllArgsConstructor
@Builder
public class ApiError {
    @Builder.Default
    private String status = HttpStatus.BAD_REQUEST.toString();
    private String reason;

    @JsonFormat(pattern = ISO_DATETIME, timezone = "UTC", shape = JsonFormat.Shape.STRING)
    private final OffsetDateTime timestamp = OffsetDateTime.now();
}
