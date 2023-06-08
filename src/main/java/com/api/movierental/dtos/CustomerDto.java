package com.api.movierental.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;


public class CustomerDto {

    @NotNull(message = "name cannot be null")
    @Pattern(regexp = "[a-zA-ZàèìòùÀÈÌÒÙáéíóúýÁÉÍÓÚÝâêîôûÂÊÎÔÛãñõÃçÇ ]+", message = "the name must not contain symbols or numbers.")
    private String name;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @CPF(message = "CPF invalid")
    @Size(min = 11, max = 11, message = "CPF must have exactly 11 characters")
    @NotNull(message = "cpf cannot be null")
    private String cpf;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}