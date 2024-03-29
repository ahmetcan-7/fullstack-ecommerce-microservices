package com.ahmetcan7.gateway.config;

import com.ahmetcan7.gateway.dto.AuthorityDto;
import com.ahmetcan7.gateway.dto.ErrorDto;
import com.ahmetcan7.gateway.dto.UserDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing authorization information");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            String[] parts = authHeader.split(" ");

            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new RuntimeException("Incorrect authorization structure");
            }

    return webClientBuilder.build()
            .post()
            .uri("http://user-service/user/validateToken?token=" + parts[1])
            .header("Authorization", authHeader)
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError,response->response.bodyToMono(ErrorDto.class)
                    .flatMap(error -> Mono.error(new RuntimeException(error.getError_message()))))
            .bodyToMono(UserDto.class)
            .map(userDto -> {
                exchange.getRequest()
                        .mutate()
                        .header("userId", userDto.getUserId());

                exchange.getRequest()
                        .mutate()
                        .header("authorities", userDto.getAuthorities().stream()
                                .map(AuthorityDto::getAuthority).reduce("", (a, b) -> a + "," + b));
                exchange.getRequest()
                        .mutate()
                        .header("username", userDto.getUsername());

                return exchange;
            }).flatMap(chain::filter);

        };
    }

    public static class Config {
        // empty class as I don't need any particular configuration
    }

}