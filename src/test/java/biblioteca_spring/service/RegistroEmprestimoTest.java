package biblioteca_spring.service;

import biblioteca_spring.repository.RegistrosRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegistroEmprestimoTest {

    @Mock
    RegistrosRepository registrosRepository;

    @InjectMocks
    EmprestimoService emprestimoService;


}
