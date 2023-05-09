package com.api.movierental.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

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
    private Boolean isActive = true;
}
