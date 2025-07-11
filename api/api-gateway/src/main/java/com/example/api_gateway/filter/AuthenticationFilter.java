
package com.example.api_gateway.filter;

import com.example.api_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtUtil jwtUtil;

    public AuthenticationFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (ServerWebExchange exchange, GatewayFilterChain chain) -> {
            // Skip validation for open routes (e.g., login, register)
            if (isSecured(exchange)) {
                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new RuntimeException("Missing or invalid Authorization header");
                }

                String token = authHeader.substring(7); // Extract token from "Bearer <token>"
         
                try {
                    // Validate the token and extract the role in one method
                    String role = jwtUtil.validateAndExtractRole(token); 
                    

                    // Get request method and path
                    String method = exchange.getRequest().getMethod().toString();  
                  
                    String path = exchange.getRequest().getURI().getPath();
                   

                    // Perform role-based access control for specific HTTP methods
                    if (!hasPermissionForRole(method, path, role)) {
                        throw new RuntimeException("Unauthorized access: Insufficient permissions");
                    }

                } catch (Exception e) {
                    throw new RuntimeException("Unauthorized access: Invalid token");
                }
            }
            return chain.filter(exchange); // Proceed to the next filter
        };
    }

    private boolean isSecured(ServerWebExchange exchange) {
        // Skip validation for certain public paths
        String path = exchange.getRequest().getURI().getPath();
        return !path.startsWith("/auth/"); // Skip validation for auth paths like /auth/login, /auth/register
    }

    private boolean hasPermissionForRole(String method, String path, String role) {
        // Check permissions based on HTTP method and the path
        if (method.equals("GET")) {
            if (path.startsWith("/api/rooms")) {
                return role.equals("OWNER") || role.equals("MANAGER") || role.equals("RECEPTIONIST");
            }
            if (path.startsWith("/api/reservations")) {
                return role.equals("OWNER") || role.equals("MANAGER") || role.equals("RECEPTIONIST");
            }
            if (path.startsWith("/api/staff")) {
                return role.equals("OWNER") || role.equals("MANAGER");
            }
            if (path.startsWith("/api/guests")) {
                return role.equals("OWNER") || role.equals("MANAGER") || role.equals("RECEPTIONIST");
            }
            if (path.startsWith("/api/bills")) {
                return role.equals("OWNER") || role.equals("MANAGER") || role.equals("RECEPTIONIST");
            }
            if (path.startsWith("/api/payments")) {
                return role.equals("OWNER") || role.equals("MANAGER") || role.equals("RECEPTIONIST");
            }
        }
        if (method.equals("POST")) {
            if (path.startsWith("/api/rooms")) {
                return role.equals("OWNER") || role.equals("MANAGER");
            }
            if (path.startsWith("/api/reservations")) {
                return role.equals("OWNER") || role.equals("MANAGER") || role.equals("RECEPTIONIST");
            }
            if (path.startsWith("/api/staff")) {
                return role.equals("OWNER");
            }
            if (path.startsWith("/api/guests")) {
                return role.equals("OWNER") || role.equals("MANAGER") || role.equals("RECEPTIONIST");
            }
            if (path.startsWith("/api/bills")) {
                return role.equals("OWNER") || role.equals("MANAGER") || role.equals("RECEPTIONIST");
            }
            if (path.startsWith("/payment")) {
                return role.equals("OWNER") || role.equals("MANAGER") || role.equals("RECEPTIONIST");
            }
        }
        if (method.equals("PUT")) {
            if (path.startsWith("/api/rooms")) {
                return role.equals("OWNER") || role.equals("MANAGER");
            }
            if (path.startsWith("/api/reservations")) {
                return role.equals("OWNER") || role.equals("MANAGER") || role.equals("RECEPTIONIST");
            }
            if (path.startsWith("/api/staff")) {
                return role.equals("OWNER");
            }
            if (path.startsWith("/api/guests")) {
                return role.equals("OWNER") || role.equals("MANAGER") || role.equals("RECEPTIONIST");
            }
            if (path.startsWith("/api/bills")) {
                return role.equals("OWNER") || role.equals("MANAGER");
            }
            if (path.startsWith("/api/payments")) {
                return role.equals("OWNER") || role.equals("MANAGER");
            }
        }
        if (method.equals("DELETE")) {
            if (path.startsWith("/api/rooms")) {
                return role.equals("OWNER");
            }
            if (path.startsWith("/api/reservations")) {
                return role.equals("OWNER") || role.equals("MANAGER");
            }
            if (path.startsWith("/api/staff")) {
                return role.equals("OWNER");
            }
            if (path.startsWith("/api/guests")) {
                return role.equals("OWNER") || role.equals("MANAGER");
            }
            if (path.startsWith("/api/bills")) {
                return role.equals("OWNER");
            }
            if (path.startsWith("/api/payments")) {
                return role.equals("OWNER");
            }
        }


        return false; // Default deny use this
    }

    public static class Config {
    }
}
