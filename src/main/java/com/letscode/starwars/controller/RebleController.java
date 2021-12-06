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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * EndPoint de acesso a recurso permission
 * @author Wenceslau
 *
 */
@RestController
@RequestMapping("/rebel")
@Tag(name = "Rebeldes da resistência")
public class RebleController extends Base {

	@Autowired
	private RebelService rebelService;

	@GetMapping("/listAll")
	@Operation(summary = "Lista todos os rebeldes da resistência")
	public List<Rebel> listAll() {
		return rebelService.listAll(true);
	}

	@PostMapping("/findByCode/{code}")
	@Operation(summary = "Localiza um rebelde da resistência pelo código")
	public ResponseEntity<Rebel> findByCode(@PathVariable Long code) {
		return ResponseEntity.status(HttpStatus.CREATED).body(rebelService.findByCode(code));
	}

	@PostMapping("/save")
	@Operation(summary = "Adiciona um rebelde a resistência")
	public ResponseEntity<Rebel> save(@RequestBody Rebel rebel) {
		return ResponseEntity.status(HttpStatus.CREATED).body(rebelService.save(rebel));
	}

	@PutMapping("updateLocalization/{code}")
	@Operation(summary = "Atualiza a localização de um rebel")
	public ResponseEntity<Rebel> updateLocalization(@PathVariable Long code, @RequestBody Localization localization) {
		return ResponseEntity.status(HttpStatus.OK).body(rebelService.updateLocalization(code, localization));
	}

	@PutMapping("consultExchangeResousce/{codeRebelOffer}/{codeRevelRequest}")
	@Operation(summary = "Realiza uma consulta de negociação de recursos entre os rebeldes")
	public  List<ResourceQuantityDTO> consultExchangeResousce(@PathVariable Long codeRebelOffer,
													@PathVariable Long codeRevelRequest,
													@RequestBody List<ResourceQuantityDTO> offers,
													@RequestBody List<ResourceQuantityDTO> requests) {
		return rebelService.executeExchangeResource(codeRebelOffer, codeRevelRequest, offers, requests);
	}

	@PutMapping("/markAsTraitor/{reportedByCode}/{suspectCode}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Informa que um rebelde se tornou traidor da resistência",
			   description ="reportedByCode = Código do rebelde que esta reportando a suspeita de traição, suspectCode = código do rebelde suspeito e traição" )
	public void markAsTraitor(@PathVariable Long reportedByCode, @PathVariable Long suspectCode) {
		rebelService.markAsTraitor(reportedByCode, suspectCode);
	}
}


