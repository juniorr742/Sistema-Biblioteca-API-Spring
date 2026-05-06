package biblioteca_spring.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CalculadoraMultaTest {

    @Test
    void diasMaiorQuePrazo(){
        long diasCorridos = 8;
        CalculadoraMulta calculadoraMulta = new CalculadoraMulta();

        double resultado = calculadoraMulta.valorCalculado(diasCorridos);

        assertEquals(2.0, resultado, 0.01);
    }

    @Test
    void diasMenorQuePrazo(){
        long diasCorridos = 6;
        CalculadoraMulta calculadoraMulta = new CalculadoraMulta();

        double resultado = calculadoraMulta.valorCalculado(diasCorridos);

        assertEquals(0, resultado, 0.01);
    }
}
