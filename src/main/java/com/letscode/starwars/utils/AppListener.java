package com.letscode.starwars.utils;

import com.letscode.starwars.base.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Classe para escutar eventos da aplicacao na inicializacao do container
 * @author Wenceslau
 *
 */
@Component
public class AppListener extends Base {

	/*
	 * Contexto da aplicacao inicializada injetado pelo spring
	 * eh atribuido o contexto defindo na classe UtilsCore
	 * ver comentario dentro do metodo applicationReadyEvent
	 */
	@Autowired
	private ApplicationContext context;

	/**
	 * Evento disparado quando a aplicacao esta inicilizada e disponivel
	 * @param event event
	 */
	@EventListener
	private void applicationReadyEvent(ApplicationReadyEvent event) {

		this.info("AppListener.applicationReadyEvent() - INIT");

		// O contexto eh atribuido a variavel statica da aplicacao
		// As classes que possuem a notacao @EntityListeners, sao classes do Hibernate
		// O Hibernate nao tem acesso ao contexto do Spring, e o contexto eh necessario nos
		// nos metodos das classes com essa notacao.
		Utils.context = context;

		if (context != null)
			this.info("Utils.context.getId(): " + Utils.context.getId());

		this.info("AppListener.applicationReadyEvent() - END");

	}

	/**
	 * Evento disparado quando a aplicacao esta inicializada
	 * @param event event
	 */
	@EventListener
	private void applicationStartedEvent(ApplicationStartedEvent event) {
		this.info("AppListener.applicationStartedEvent()");
	}

	/**
	 * Evento disparado quando a aplicacao esta sendo inicializada
	 * @param event event
	 */
	@EventListener
	private void applicationStartingEvent(ApplicationStartingEvent event) {
		this.info("AppListener.applicationStartingEvent()");
	}

}
