package petro.post_hub.service;

import petro.post_hub.model.dto.UserProfileDto;
import petro.post_hub.model.request.LoginRequest;
import petro.post_hub.model.request.RegistrationRequest;
import petro.post_hub.model.response.CustomResponse;

public interface AuthService {
    CustomResponse<UserProfileDto> login(LoginRequest loginRequest);

    CustomResponse<UserProfileDto> registration(RegistrationRequest registrationRequest);

    CustomResponse<UserProfileDto> refreshToken(String refreshToken);
}
