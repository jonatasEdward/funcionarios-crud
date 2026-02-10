package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Funcionario {
    private static int contadorId = 1;

    private int id;
    private String nome;
    private String email;
    private String cpf;
    private double salario;
    private LocalDate dataContratacao;

    public Funcionario(String nome, String email, String cpf, double salario) {
        this.id = contadorId++;
        setNome(nome);
        setEmail(email);
        setCpf(cpf);
        setSalario(salario);
        this.dataContratacao = LocalDate.now();
    }

    // Validações no setter - Aplicação de POO
    public void setNome(String nome) {
        if (nome == null || nome.trim().length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres");
        }
        this.nome = nome.trim();
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email.toLowerCase().trim();
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.replaceAll("[^0-9]", "").length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos");
        }
        this.cpf = cpf.replaceAll("[^0-9]", "");
    }

    public void setSalario(double salario) {
        if (salario < 1320.0) { // Salário mínimo 2025
            throw new IllegalArgumentException("Salário não pode ser menor que o mínimo");
        }
        this.salario = salario;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public double getSalario() {
        return salario;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    // Métodos de negócio
    public void aumentarSalario(double percentual) {
        if (percentual > 0 && percentual <= 100) {
            this.salario *= (1 + percentual / 100);
        } else {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 100");
        }
    }

    public String getCpfFormatado() {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    @Override
    public String toString() {
        return String.format("Funcionario{id=%d, nome='%s', email='%s', cpf='%s', salario=%.2f, dataContratacao=%s}",
                id, nome, email, getCpfFormatado(), salario, dataContratacao);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Funcionario that = (Funcionario) obj;
        return Objects.equals(cpf, that.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
