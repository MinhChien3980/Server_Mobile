package org.example.server_mobile.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.ContactRequest;
import org.example.server_mobile.dto.response.ContactResponse;
import org.example.server_mobile.entity.Contact;
import org.example.server_mobile.mapper.ContactMapper;
import org.example.server_mobile.repository.ContactRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContactService implements IService<ContactRequest, ContactResponse> {
    Logger log = LoggerFactory.getLogger(ContactService.class);
    ContactRepo contactRepo;
    ContactMapper contactMapper;

    @Override
    public ContactResponse create(ContactRequest contactRequest) {
        Contact contact = contactMapper.toContact(contactRequest);
        return contactMapper.toContactResponse(contactRepo.save(contact));
    }

    @Override
    public ContactResponse update(ContactRequest request) {
        return null;
    }

    @Override
    public ContactResponse findById(Long id) {
        Contact contact = contactRepo.findById(id).orElseThrow();
        return contactMapper.toContactResponse(contact);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ContactResponse> findAll() {
        return contactRepo.findAll().stream().map(contactMapper::toContactResponse).toList();
    }


}
