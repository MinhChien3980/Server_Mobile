package org.example.server_mobile.mapper;

import org.example.server_mobile.dto.request.ContactRequest;
import org.example.server_mobile.dto.response.ContactResponse;
import org.example.server_mobile.entity.Category;
import org.example.server_mobile.entity.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    Contact toContact(ContactRequest request);

    ContactResponse toContactResponse(Contact contact);
}
