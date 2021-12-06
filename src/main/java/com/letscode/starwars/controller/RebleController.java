package com.letscode.starwars.controller;

import com.letscode.starwars.base.Base;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.dto.ResourceCreditDTO;
import com.letscode.starwars.model.dto.ResourceDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import com.letscode.starwars.model.embedded.Localization;
import com.letscode.starwars.service.RebelService;
import com.letscode.starwars.utils.Initializer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * EndPoint de acesso a recurso permission
 * @author Wenceslau
 *
 */
@RestController
@RequestMapping("/rebel")
@Tag(name = "Controlador Rebeldes da Resistência")
public class RebleController extends Base {

	@Autowired
	private RebelService rebelService;

	@Autowired
	private Initializer initializer;

	@PostMapping("/initializer")
	@Operation(summary = "Criar massa de dados")
	public List<Rebel> initializer() {
		initializer.createRebel();
		return rebelService.listAll();
	}

	@GetMapping("/listAll")
	@Operation(summary = "Lista todos os rebeldes da resistencia")
	public List<Rebel> listAll() {
		return rebelService.listAll();
	}

	@GetMapping("/listCreditResources")
	@Operation(summary = "Lista os valores dos creditos galaticos dos recursos usandos pela resistencia")
	public List<ResourceCreditDTO> listResourceCredit() {
		return rebelService.listResourceCredit();
	}

	@PostMapping("/findByCode/{code}")
	@Operation(summary = "Localiza um rebelde da resistencia pelo codigo")
	public ResponseEntity<Rebel> findByCode(@PathVariable Long code) {
		return ResponseEntity.status(HttpStatus.CREATED).body(rebelService.findByCode(code));
	}

	@PostMapping("/save")
	@Operation(summary = "Adiciona um rebelde a resistencia")
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

	@PutMapping("/isTraitor/{code}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Informa que um rebelde se tornou triaidor da resistencia")
	public void isTraitor(@PathVariable Long code) {
		rebelService.isTraitor(code);
	}
}


