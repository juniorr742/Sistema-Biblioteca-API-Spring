package biblioteca_spring.exception;

public class BusinessException extends RuntimeException{

    public BusinessException(String mensagem){
        super(mensagem);
    }
}
