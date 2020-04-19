package pl.exam.app.configuration.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.SneakyThrows;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;


public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        if (request.getRequestURI().contains("/login")) {
            chain.doFilter(request, response);
            return;
        }
        String authorizationToken = Optional.ofNullable(request.getHeader("Authorization"))
                .orElseThrow(() -> new RuntimeException("No authorization token provided"));
        DecodedJWT decoded = JWT.decode(authorizationToken);
        verifyJWT(decoded);
        if (hasTokenExpired(decoded)) {
            throw new RuntimeException("Token has expired");
        }
        chain.doFilter(request, response);
    }

    private void verifyJWT(DecodedJWT decoded) throws InvalidKeySpecException, NoSuchAlgorithmException {
        RSAPublicKey publicKey = getPublicKey(decoded);
        JWT.require(Algorithm.RSA256(publicKey, null))
                .build()
                .verify(decoded);
    }

    private RSAPublicKey getPublicKey(DecodedJWT decoded) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Claim base64EncodedPublicKey = decoded.getHeaderClaim("publicKey");
        byte[] publicKey = Base64.getDecoder().decode(base64EncodedPublicKey.asString().getBytes());
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey));
    }

    private boolean hasTokenExpired(DecodedJWT jwt) {
        return jwt.getExpiresAt().after(Date.from(Instant.now()));
    }
}
