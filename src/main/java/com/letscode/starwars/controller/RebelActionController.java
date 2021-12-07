package com.letscode.starwars.controller;

import com.letscode.starwars.base.Base;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.RebelAction;
import com.letscode.starwars.service.interfaces.RebelActionService;
import com.letscode.starwars.service.interfaces.RebelService;
import com.letscode.starwars.utils.Initializer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * End point para verificar o historico de alterações e transações dos rebeldes
 * @author Wenceslau
 *
 */
@RestController
@RequestMapping("/rebelAction")
@Tag(name = "Histórico de ações e negociações dos rebeldes")
public class RebelActionController extends Base {

	@Autowired
	private RebelActionService rebelActionService;

	@GetMapping("/list")
	@Operation(summary = "Lista todas as ações executadas pelos rebeldes")
	public List<RebelAction> listAll() {
		return rebelActionService.list();
	}

	@GetMapping("/listByRebel/{rebelCode}")
	@Operation(summary = "Lista todas as ações executadas por um rebelde em ordem recente")
	public List<RebelAction> listByRebel(@PathVariable Long rebelCode) {
		return rebelActionService.listByRebel(rebelCode);
	}

}


