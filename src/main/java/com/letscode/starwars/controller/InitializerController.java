package com.letscode.starwars.controller;

import com.letscode.starwars.base.Base;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.dto.ResourceCreditDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import com.letscode.starwars.model.embedded.Localization;
import com.letscode.starwars.service.interfaces.RebelService;
import com.letscode.starwars.utils.Initializer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * EndPoint de acesso a recurso permission
 * @author Wenceslau
 *
 */
@RestController
@RequestMapping("/initializer")
@Tag(name = "Inicializador de dados")
public class InitializerController extends Base {

	@Autowired
	private RebelService rebelService;

	@Autowired
	private Initializer initializer;

	@PostMapping("/initializer")
	@Operation(summary = "Criar massa de dados")
	public List<Rebel> initializer() {
		initializer.createRebel();
		return rebelService.listAll(false);
	}

}


