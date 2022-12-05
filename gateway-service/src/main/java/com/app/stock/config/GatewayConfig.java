package com.app.stock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.stock.filter.JwtAuthenticationFilter;

@Configuration
public class GatewayConfig {

	@Autowired
	private JwtAuthenticationFilter filter;
	
	@Bean
	public RouteLocator configureRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(
						"productId", 
						r->r.path("/api/product/**")
						.filters(f -> f.filter(filter))
						.uri("lb://PRODUCT-SERVICE")
						)
				.route("authId", 
						r -> r.path("/auth/**")
						.filters(f -> f.filter(filter))
						.uri("lb://AUTH-SERVICE"))
				.route("userId", 
						r -> r.path("/users/**")
						.filters(f -> f.filter(filter))
						.uri("lb://USER-SERVICE"))
				.route("stockId", 
						r -> r.path("/company/**")
						.filters(f -> f.filter(filter))
						.uri("lb://STOCK-SERVICE"))
				.build();
		
	}
}
