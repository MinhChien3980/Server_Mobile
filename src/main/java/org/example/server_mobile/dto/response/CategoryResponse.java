package org.example.server_mobile.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.entity.enums.AgeGroupCategory;
import org.example.server_mobile.entity.enums.Gender;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CategoryResponse {
    Long id;
    String name;
    String description;
    @Enumerated(EnumType.STRING)
    AgeGroupCategory age;
    @Enumerated(EnumType.STRING)
    Gender gender;


}
