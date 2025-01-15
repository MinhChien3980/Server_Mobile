    package org.example.server_mobile.controller;

    import lombok.RequiredArgsConstructor;
    import lombok.experimental.FieldDefaults;
    import org.example.server_mobile.dto.request.ApiResponse;
    import org.example.server_mobile.dto.request.MailRequest;
    import org.example.server_mobile.dto.response.MailResponse;
    import org.example.server_mobile.service.MailSenderService;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/mail")
    @RequiredArgsConstructor
    @FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
    public class MailController {
        MailSenderService mailSenderService;

        ApiResponse<MailResponse> sendMail(@RequestBody MailRequest mailRequest) {
            mailSenderService.send(mailRequest);
            return ApiResponse.<MailResponse>builder()
                    .data(MailResponse.builder()
                            .message("Mail sent successfully")
                            .status("success")
                            .build())
                    .build();
        }

    }
