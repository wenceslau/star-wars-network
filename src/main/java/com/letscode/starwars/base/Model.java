package com.letscode.starwars.base;

import com.letscode.starwars.utils.ModelListener;
import com.letscode.starwars.utils.Utils;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(ModelListener.class)
public class Model extends Base {

    /*
     * PK do objeto
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long code;


    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(code, model.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return Utils.objectToJson(this);
    }
}
