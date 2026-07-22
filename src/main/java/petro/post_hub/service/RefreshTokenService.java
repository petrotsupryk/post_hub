package petro.post_hub.service;

import petro.post_hub.model.entity.RefreshToken;
import petro.post_hub.model.entity.User;

public interface RefreshTokenService {
    RefreshToken generateOrUpdateRefreshToken(User user);
    RefreshToken validateRefreshToken(String token);
}
