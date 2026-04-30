package biblioteca_spring.service;

import biblioteca_spring.config.BibliotecaConfig;
import org.springframework.stereotype.Service;

@Service
public class CalculadoraMulta {

    public double valorCalculado(long diasCorridos){
        if (diasCorridos < BibliotecaConfig.PRAZO_DEVOLUCO_PADRAO_DIAS){
            return 0.0;
        }
        return (diasCorridos - BibliotecaConfig.PRAZO_DEVOLUCO_PADRAO_DIAS) * BibliotecaConfig.VALOR_MULTA_DIARIA;
    }
}
