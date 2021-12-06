package com.letscode.starwars.model.embedded;

import com.letscode.starwars.base.Base;
import com.letscode.starwars.base.Model;
import com.letscode.starwars.utils.ModelListener;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Localization {
    @Column
    private String longitude;

    @Column
    private String latitude;

    @Column
    private String galaxy;
}
