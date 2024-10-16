package org.example.server_mobile.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.entity.Role;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UserCreationRequest {
    String name;
    String email;
    @Size(min = 8, max = 20, message = "INVALID_PASSWORD")
    String password;
    Set<Role> role;
    LocalDate dateOfBirth;
    String status;
    Date created_at;
    Date updated_at;

}


