package com.omar.time.dto.jwt;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthDto {
	
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthDto(String accessToken) {
        this.accessToken = accessToken;
    }

}
