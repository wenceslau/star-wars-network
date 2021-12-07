
package com.letscode.starwars.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rebelAction")
public class RebelAction {

	/*
	 * PK do registro
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	protected Long code;

	/*
	 * Nome da acao do rebelde. UPDATE INSERT DELETE
	 */
	@Column
	private String action;

	/*
	 * Nome do objeto que esta sofrendo a acao
	 */
	@Column
	private String nameObject;

	/*
	 * ID do objeto, PK do registro na tabela
	 */
	@Column
	private Long idRecord;

	/*
	 * Objeto em formato JSON. O registro que esta sofrendo a acao
	 */
	@Column(columnDefinition="MEDIUMTEXT")
	private String valueRecord;

	/*
	 * Data do ação
	 */
	@Column
	private LocalDateTime dateRecord;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RebelAction that = (RebelAction) o;
		return code.equals(that.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}
}