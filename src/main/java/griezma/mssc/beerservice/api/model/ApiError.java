package griezma.mssc.beerservice.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@Builder
public class ApiError {
    @Builder.Default
    private String status = HttpStatus.BAD_REQUEST.toString();
    private String reason;

    @JsonFormat(timezone = "UTC", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", shape = JsonFormat.Shape.STRING)
    private final OffsetDateTime timestamp = OffsetDateTime.now();
}
