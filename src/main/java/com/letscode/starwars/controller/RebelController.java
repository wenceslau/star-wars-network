package com.letscode.starwars.controller;

import com.letscode.starwars.base.Base;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.dto.ExchangeResourseDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * EndPoint para manipular dados de um rebelde
 * @author Wenceslau
 *
 */
@RestController
@RequestMapping("/rebel")
@Tag(name = "Rebeldes da resistência")
public class RebelController extends Base {

	@Autowired
	private RebelService rebelService;

	@GetMapping("/listAll")
	@Operation(summary = "Lista todos os rebeldes da resistência")
	public List<Rebel> listAll() {
		return rebelService.listAll(true);
	}

	@PostMapping("/findByCode/{rebelCode}")
	@Operation(summary = "Localiza um rebelde da resistência pelo código")
	public ResponseEntity<Rebel> findByCode(@PathVariable Long rebelCode) {
		return ResponseEntity.status(HttpStatus.CREATED).body(rebelService.findByCode(rebelCode));
	}

	@PostMapping("/insert")
	@Operation(summary = "Adiciona um rebelde a resistência")
	public ResponseEntity<Rebel> insert(@RequestBody Rebel rebel) {
		return ResponseEntity.status(HttpStatus.CREATED).body(rebelService.insert(rebel));
	}

	@PutMapping("updateLocalization/{rebelCode}")
	@Operation(summary = "Atualiza a localização de um rebel")
	public ResponseEntity<Rebel> updateLocalization(@PathVariable Long rebelCode, @RequestBody Localization localization) {
		return ResponseEntity.status(HttpStatus.OK).body(rebelService.updateLocalization(rebelCode, localization));
	}

	@PutMapping("executeExchangeResource/{codeRebelOffer}/{codeRebelRequest}")
	@Operation(summary     = "Realiza uma consulta de negociação de recursos entre os rebeldes",
			   description = "codeRebelOffer = [Código do rebelde que esta oferecendo recursos] " +
					  		 "codeRebelRequest = [Código do rebelde para qual se esta requisitando recursos]")
	public  ResponseEntity<Rebel> executeExchangeResource(@PathVariable Long codeRebelOffer, @PathVariable Long codeRebelRequest,
													@RequestBody ExchangeResourseDTO exchangeResourseDTO) {
		Rebel rebel =  rebelService.executeExchangeResource(codeRebelOffer, codeRebelRequest, exchangeResourseDTO);
		return ResponseEntity.status(HttpStatus.OK).body(rebel);
	}

	@PutMapping("/markAsTraitor/{reportedByCode}/{suspectCode}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary     = "Informa que um rebelde se tornou traidor da resistência",
			   description = "reportedByCode = [Código do rebelde que esta reportando a suspeita de traição] " +
					         "suspectCode = [código do rebelde suspeito de traição]" )
	public void markAsTraitor(@PathVariable Long reportedByCode, @PathVariable Long suspectCode) {
		rebelService.markAsTraitor(reportedByCode, suspectCode);
	}
}


