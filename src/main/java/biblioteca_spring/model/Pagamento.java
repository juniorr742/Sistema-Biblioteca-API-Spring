package biblioteca_spring.model;

import jakarta.persistence.Embeddable;


import java.time.LocalDateTime;

@Embeddable
public class Pagamento {
    private double saldoDevedor;
    private LocalDateTime dataUltimaOperacao;

    public Pagamento(){
        this.saldoDevedor = 0;
        this.dataUltimaOperacao = LocalDateTime.now();
    }

    public double getSaldoDevedor() {
        return saldoDevedor;
    }

    public LocalDateTime getDataUltimaOperacao() {
        return dataUltimaOperacao;
    }

    public void aumentarDebito(double valor){
        if (valor > 0){
            this.saldoDevedor += valor;
            atualizarData();
        }else {
            throw new RuntimeException("ERRO: valor negativo!");
        }
    }

    public void reduzirValor(double valor){
        if (valor > 0 && valor <= this.saldoDevedor){
            this.saldoDevedor -= valor;
            atualizarData();
        }else if (valor > this.saldoDevedor){
            throw new RuntimeException("ERRO: Pagamento maior que o saldo devedor.");
        }else {
            throw new RuntimeException("ERRO: Valor negativo.");
        }
    }

    public void quitarTotalmente(){
        this.saldoDevedor = 0;
        atualizarData();
    }

    public void atualizarData(){
        this.dataUltimaOperacao = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "R$ " + this.saldoDevedor;
    }
}
