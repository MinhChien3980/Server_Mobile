package org.example.server_mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.entity.User;

import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ContactResponse {
    Long id;
    String description;// Noi dung feedback
    User user;// id nguoi dung
    Date createdAt;// thoi gian tao feedback


}
