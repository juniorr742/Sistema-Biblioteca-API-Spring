package biblioteca_spring.security;

import biblioteca_spring.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                // diz de quem é o token
                .subject(usuario.getEmail())
                //a dta que o token foi criado
                .issuedAt(new Date())
                //a data que vai ser expirado, aqui coloquei 24hrs
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                //cripstografia com algoritmo hmac
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String validarToken(String token) {
        return Jwts.parser()
                // verifica a assinatura com a mesma chave secreta usada pra gerar
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                // constrói o parser
                .build()
                // faz o parse do token — lança exceção se inválido ou expirado
                .parseSignedClaims(token)
                // acessa o payload — onde estão os dados (claims)
                .getPayload()
                // extrai o valor do campo "sub" como String — o ID do usuário
                .get("sub", String.class);
    }
}
