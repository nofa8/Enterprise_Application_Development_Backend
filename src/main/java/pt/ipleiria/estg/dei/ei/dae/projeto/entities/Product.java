package pt.ipleiria.estg.dei.ei.dae.projeto.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome"})
}
)
public class Produto  extends Versionable {

    @Id
    private long  code;
    @NotBlank
    private String nome;
    @NotBlank
    private String marca;


    public Produto(long code, String nome, String marca) {
        this.code = code;
        this.nome = nome;
        this.marca = marca;
    }

    public Produto() {
    }


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    public @NotBlank String getMarca() {
        return marca;
    }

    public void setMarca(@NotBlank String marca) {
        this.marca = marca;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode());
    }

    @Override
    public boolean equals(Object obj) {
        return this.getCode() == ((Produto)obj).getCode();
    }

}
