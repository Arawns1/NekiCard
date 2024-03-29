package br.com.itneki.nekicard.socialmedia.dto;

import br.com.itneki.nekicard.socialmedia.domain.SocialMediaNames;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record SaveSocialMediaDTO(
                                @Schema(example = "Ex.: LINKEDIN, FACEBOOK, GITHUB",
                                        requiredMode = Schema.RequiredMode.REQUIRED)
                                String name,
                                @Schema(example = "https://linkedin.com.br/user",
                                        requiredMode = Schema.RequiredMode.REQUIRED)
                                String url) {
}
