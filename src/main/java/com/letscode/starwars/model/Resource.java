package com.letscode.starwars.model;

import com.letscode.starwars.base.Model;
import com.letscode.starwars.model.Enuns.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resource")
public class Resource extends Model {

    @Column
    private ResourceType resourceType;

    @Column
    private int quantity;

    /*
     * Rebeled qual o recurso pertence
     */
    @ManyToOne
    @JoinColumn(name = "rebelFk")
    private Rebel rebel;

}
