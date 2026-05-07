package biblioteca_spring.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public final class BibliotecaConfig {

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private BibliotecaConfig(){}

    public static final double CUSTO_FIXO_EMPRESTIMO = 15;
    public static final double VALOR_MULTA_DIARIA = 2;
    public static final int PRAZO_DEVOLUCO_PADRAO_DIAS =7;

}
