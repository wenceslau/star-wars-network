package com.letscode.starwars.utils;

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
public class AppListener {

	/*
	 * Contexto da aplicacao inicializada injetado pelo spring
	 * eh atribuido o contexto defindo na classe UtilsCore
	 * ver comentario dentro do metodo applicationReadyEvent
	 */
	@Autowired
	private ApplicationContext context;

	/**
	 * Evento disparado quando a aplicacao esta inicilizada e disponivel
	 * @param event
	 */
	@EventListener
	private void applicationReadyEvent(ApplicationReadyEvent event) {

		System.out.println(LocalDateTime.now() + " ApplicationListener.applicationReadyEvent() - INIT");

		// O contexto eh atribuido a variavel statica da aplicacao
		// As classes que possuem a notacao @EntityListeners, sao classes do Hibernate
		// O Hibernate nao tem acesso ao contexto do Spring, e o contexto eh necessario nos
		// nos metodos das classes com essa notacao.
		Utils.context = context;

		if (context != null)
			System.out.println("Utils.context.getId(): " + Utils.context.getId());

		System.out.println(LocalDateTime.now() + " CoreAppListener.applicationReadyEvent() - END");

	}

	/**
	 * Evento disparado quando a aplicacao esta inicializada
	 * @param event
	 */
	@EventListener
	private void applicationStartedEvent(ApplicationStartedEvent event) {
		System.out.println(LocalDateTime.now() + " CoreAppListener.applicationStartedEvent()");
	}

	/**
	 * Evento disparado quando a aplicacao esta sendo inicializada
	 * @param event
	 */
	@EventListener
	private void applicationStartingEvent(ApplicationStartingEvent event) {
		System.out.println(LocalDateTime.now() + " CoreAppListener.applicationStartingEvent()");
	}

}
