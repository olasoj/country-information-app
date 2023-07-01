package com.example.countryinformationapplication.core.adapter.inbound.rest;

import com.microservice.gateway.user.individual.model.inbound.rest.IndividualUserRestInboundUpdateRequestBody;
import com.microservice.gateway.user.individual.model.inbound.rest.NewIndividualUserRequest;
import com.microservice.gateway.user.individual.model.outbound.rest.response.creation.IndividualUserCreationRestOutboundResponseData;
import com.microservice.gateway.user.individual.model.outbound.rest.response.view.IndividualUserViewRestOutboundResponseData;
import com.microservice.gateway.user.individual.service.IndividualUserService;
import com.microservice.gateway.user.main.model.internal.valueobject.UserPrincipal;
import com.microservice.gateway.util.model.response.model.Response;
import com.microservice.gateway.util.model.response.transformer.ResponseAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "/user/individual")
public class IndividualController {
    private final IndividualUserService individualUserService;

    public IndividualController(IndividualUserService individualUserService) {
        this.individualUserService = individualUserService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Response<IndividualUserCreationRestOutboundResponseData>> registerUser(@Valid @RequestBody NewIndividualUserRequest individualUserCreationRequest) throws URISyntaxException {
        var registerUser = individualUserService.registerUser(individualUserCreationRequest);
        var individualUserCreationResponseDataResponse = ResponseAssembler.toResponse(HttpStatus.CREATED, registerUser);
        return ResponseEntity.created(new URI("")).body(individualUserCreationResponseDataResponse);
    }

    @GetMapping(path = "/")
    public ResponseEntity<Response<IndividualUserViewRestOutboundResponseData>> getUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        var individualUser = individualUserService.getUser(userPrincipal.getUserId());
        var individualUserCreationResponseDataResponse = ResponseAssembler.toResponse(HttpStatus.OK, individualUser);
        return ResponseEntity.ok().body(individualUserCreationResponseDataResponse);
    }

    @PutMapping(path = "/")
    public ResponseEntity<Response<IndividualUserViewRestOutboundResponseData>> updateUser(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody IndividualUserRestInboundUpdateRequestBody individualUserRestInboundUpdateRequestBody) {
        var updateUser = individualUserService.updateUser(userPrincipal.getUserId(), individualUserRestInboundUpdateRequestBody);
        var individualUserCreationResponseDataResponse = ResponseAssembler.toResponse(HttpStatus.OK, updateUser);
        return ResponseEntity.ok().body(individualUserCreationResponseDataResponse);
    }

}
