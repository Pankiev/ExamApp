package pl.exam.app.configuration.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.SneakyThrows;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.*;

class JWTGenerator {
    String generate(String username, List<String> authorities) {
        Date expirationDate = getExpirationDate();
        KeyPair keyPair = getRsaKeyPair();
        Map<String, Object> publicKeyHeader = Map.of("publicKey",
                Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(expirationDate)
                .withClaim("Authorities", authorities)
                .withHeader(publicKeyHeader)
                .withIssuedAt(Date.from(Instant.now()))
                .sign(Algorithm.RSA256((RSAPublicKey)keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate()));
    }

    @SneakyThrows
    private KeyPair getRsaKeyPair() {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        return kpg.generateKeyPair();
    }

    private Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        return calendar.getTime();
    }
}
