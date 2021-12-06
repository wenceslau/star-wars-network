package com.letscode.starwars.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.letscode.starwars.base.Model;
import com.letscode.starwars.model.embedded.Localization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rebel")
public class Rebel extends Model {
    @Column
    private String name;

    @Column
    private Integer age;

    @Column
    @Enumerated(EnumType.STRING)
    private Enuns.Gender gender;

    @Embedded
    private Localization Localization;

    @Column
    private Integer suspect;

    /*
     * Lista de recursos
     * A notacao orphanRemoval, quando o objeto pai for deletado, deleta tb o objeto filho, diferente de delete_cascade
     * OneToMany, migra a chave do pai para a tabela filho
     */
    @OneToMany(mappedBy = "rebel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("rebel") // Ignora dentro do objeto property o objeto service, evita overflow no json
    private List<Resource> resources;

}
