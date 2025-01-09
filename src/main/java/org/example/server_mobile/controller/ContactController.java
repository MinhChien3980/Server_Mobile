package org.example.server_mobile.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.server_mobile.dto.request.ApiResponse;
import org.example.server_mobile.dto.request.ContactRequest;
import org.example.server_mobile.dto.response.ContactResponse;
import org.example.server_mobile.service.ContactService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ContactController implements IController<ContactRequest, ContactResponse> {

    ContactService ContactService;

    @PostMapping("/create")
    public ApiResponse<ContactResponse> create(@RequestBody ContactRequest contactRequest) {
        ContactResponse ContactResponse = ContactService.create(contactRequest);
        return ApiResponse.<ContactResponse>builder()
                .data(ContactResponse)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ContactResponse> findById(@PathVariable Long id) {
        ContactResponse ContactResponse = ContactService.findById(id);
        return ApiResponse.<ContactResponse>builder()
                .data(ContactResponse)
                .build();
    }


    @GetMapping
    public ApiResponse<List<ContactResponse>> findAll() {
        List<ContactResponse> categories = ContactService.findAll();
        return ApiResponse.<List<ContactResponse>>builder()
                .code(200)
                .data(categories)
                .build();
    }

    @Override
    public ApiResponse<String> update(Long id, ContactRequest request) {
        return null;
    }

    @Override
    public ApiResponse<String> delete(Long id) {
        return null;
    }
}
