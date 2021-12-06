package com.letscode.starwars.controller;

import com.letscode.starwars.base.Base;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.dto.AverageResourcesDTO;
import com.letscode.starwars.model.dto.CreditsLostDTO;
import com.letscode.starwars.model.dto.PercentTraitorsDTO;
import com.letscode.starwars.model.dto.PercentsRebelDTO;
import com.letscode.starwars.model.dto.ResourceCreditDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import com.letscode.starwars.model.embedded.Localization;
import com.letscode.starwars.service.interfaces.RebelService;
import com.letscode.starwars.service.interfaces.ReportService;
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
@RequestMapping("/report")
@Tag(name = "Relatórios")
public class ReportController extends Base {

	@Autowired
	private ReportService reportService;

	@GetMapping("/reportResourceCredit")
	@Operation(summary = "Lista os valores dos créditos galácticos dos recursos usados pela resistência")
	public List<ResourceCreditDTO> reportResourceCredit() {
		return reportService.reportResourceCredit();
	}

	@GetMapping("/reportPercentTraitor")
	@Operation(summary = "Relatório de porcentagem de traidores")
	public PercentTraitorsDTO reportPercentTraitor() {
		return reportService.reportPercentTraitor();
	}

	@GetMapping("/reportPercentRebel")
	@Operation(summary = "Relatório de porcentagem de rebeldes")
	public PercentsRebelDTO reportPercentRebel() {
		return reportService.reportPercentRebel();
	}

	@GetMapping("/reportAverageResources")
	@Operation(summary = "Relatório quantidade média de cada tipo de recurso por rebelde")
	public AverageResourcesDTO reportAverageResources() {
		return reportService.reportAverageResources();
	}

	@GetMapping("/reportCreditLost")
	@Operation(summary = "Relatorio pontos perdidos devido a traidores")
	public CreditsLostDTO reportCreditLost() {
		return reportService.reportCreditLost();
	}


}


