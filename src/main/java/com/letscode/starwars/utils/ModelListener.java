
package com.letscode.starwars.utils;

import com.letscode.starwars.base.Model;
import com.letscode.starwars.model.RebelAction;
import com.letscode.starwars.service.interfaces.RebelActionService;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Classe listener dos metodos JPA Insert Update Delete
 * Eh disparado a cada acao ocorrida por esses metodos
 * As entidades precisam estar anotadas referenciado esta classe
 * @author Wenceslau
 */
public class ModelListener {

	/*
	 * Esse service nao esta anotado com @utowired porque essa classe
	 * eh gerenciada pelo hibernate e mesmo que essa classe seja anotada
	 * com @service ou @component, quando o hibernate disparar eventos
	 * para ela, o contexto do spring nao sera encontrado.
	 * O objeto eh instanciado usando o metodo estatico do UtilsCore
	 * que procura no contexto do spring porque possui uma variavel estatica que
	 * contem o contexto
	 */
	private RebelActionService userActionService;

	/**
	 * Evento sincrono disparado pelo hibernate quando uma entidade eh persistida no repositorio
	 * Qualquer execcecao disparada nessse metodo causa roolback de houver transacao no insert
	 * @param model entidade
	 */
	@PostPersist
	public void postPersist(Model model) {
		saveUserAction(model, "INSERT");
	}

	/**
	 * Evento sincrono disparado pelo hibernate quando uma entidade eh removida do repositorio
	 * Qualquer excecao disparada nesse metodo causa rollback de houver transacao no insert
	 * @param model entidade
	 */
	@PostRemove
	public void postRemove(Model model) {
		saveUserAction(model, "DELETE");
	}

	/**
	 * Evento sincrono disparado pelo hibernate quando uma entidade eh atualizada no repositorio
	 * Qualquer excecao disparada nesse metodo causa rollback de houver transacao no insert
	 * @param model entidade
	 */
	@PostUpdate
	public void postUpdate(Model model) {
		saveUserAction(model, "UPDATE");

	}

	/**
	 * Procura no contexto o bean userActionService
	 * Atribuido ao variaval de instancia da classe
	 * O bean eh procurado usando o contexto do spring que esta disponivel
	 * na classe Utils. o Contexto foi atribuido a uma classe estatica
	 * no momento da inicializacao da aplicacao
	 * @return UserActionService
	 */
	private RebelActionService getUserActionService() {
		if (userActionService == null)
			userActionService = (RebelActionService) Utils.getBean("rebelActionService");
		return userActionService;
	}

	/**
	 * Retorna um objeto RebelAction
	 * @param model rebelde
	 * @param action Insert, Update, Delete
	 * @return RebelAction
	 */
	private RebelAction getUserAction(Model model, String action){
		return RebelAction.builder()
				.action(action)
				.dateRecord(LocalDateTime.now())
				.idRecord(model.getCode())
				.valueRecord(Utils.objectToJson(model))
				.nameObject(model.getClass().getSimpleName())
				.build();
	}

	/**
	 * Salva o objeto Action
	 * @param model rebelde
	 * @param action Insert, Update, Delete
	 */
	private void saveUserAction(Model model, String action){
		getUserActionService().save(getUserAction(model, action));
	}
}
