package org.example.server_mobile.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class GenderRequest {
    //Request can den id
    Long id;

}
