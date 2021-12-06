package com.letscode.starwars.service;

import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.dto.AverageResourcesDTO;
import com.letscode.starwars.model.dto.CreditsLostDTO;
import com.letscode.starwars.model.dto.PercentTraitorsDTO;
import com.letscode.starwars.model.dto.PercentsRebelDTO;
import com.letscode.starwars.model.dto.ResourceCreditDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import com.letscode.starwars.service.interfaces.RebelService;
import com.letscode.starwars.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("reportService")
public class ReportServiceImpl implements ReportService {

    @Autowired
    public RebelService rebelService;

    @Override
    public PercentTraitorsDTO reportPercentTraitor() {

        List<Rebel> listRebel = rebelService.listAll(false);

        PercentTraitorsDTO percentTraitorsDTO = PercentTraitorsDTO.builder().build();
        percentTraitorsDTO.setTotalRebel(listRebel.size());
        int totalTraitor = 0;
        for(Rebel rebel : listRebel)
            if (rebelService.isTraitor(rebel))
                totalTraitor++;

        percentTraitorsDTO.setTotalTraitor(totalTraitor);
        BigDecimal perc = new BigDecimal(percentTraitorsDTO.getTotalTraitor()).divide(new BigDecimal(percentTraitorsDTO.getTotalRebel()));
        perc = perc.multiply(new BigDecimal(100));
        percentTraitorsDTO.setPercentTraitor(perc);

        return percentTraitorsDTO;
    }

    @Override
    public PercentsRebelDTO reportPercentRebel() {


        PercentTraitorsDTO percentTraitorsDTO = reportPercentTraitor();

        BigDecimal percTraitor = percentTraitorsDTO.getPercentTraitor();
        BigDecimal percRebel = (new BigDecimal(100).subtract(percTraitor));

        PercentsRebelDTO percentsRebelDTO = PercentsRebelDTO.builder()
                .totalRebel(percentTraitorsDTO.getTotalRebel())
                .totalTraitor(percentTraitorsDTO.getTotalTraitor())
                .percentRebel(percRebel)
                .build();

        return percentsRebelDTO;
    }

    @Override
    public AverageResourcesDTO reportAverageResources() {

        List<Rebel> listRebel = rebelService.listAll(true);

        int gun = 0 , munition = 0 , food = 0 , water = 0, totalRebel;
        totalRebel = listRebel.size();

        for(Rebel rebel : listRebel) {

            for (Resource resource : rebel.getResources()) {
                switch (resource.getResourceType()){
                    case GUN:
                        gun += resource.getQuantity();
                        break;
                    case FOOD:
                        food += resource.getQuantity();
                        break;
                    case WATER:
                        water += resource.getQuantity();
                        break;
                    case MUNITION:
                        munition += resource.getQuantity();
                        break;
                }
            }
        }

        return AverageResourcesDTO.builder()
                .guns("Média de "+gun /totalRebel +" armas por rebelde")
                .munition("Média de "+munition /totalRebel +" munições por rebelde")
                .food("Média de "+food /totalRebel +" comidas por rebelde")
                .water("Média de "+water /totalRebel +" aguas por rebelde")
                .build();
    }

    @Override
    public CreditsLostDTO reportCreditLost() {
        List<Rebel> listRebel = rebelService.listAll(false);
        Integer credLost = 0;

        CreditsLostDTO creditsLostDTO = CreditsLostDTO.builder().build();
        var listResourceQuantityDTO = new ArrayList<ResourceQuantityDTO>();
        int totalTraitor = 0;
        for(Rebel rebel : listRebel) {
            if (rebelService.isTraitor(rebel)) {
                totalTraitor++;
                for (Resource resource : rebel.getResources()) {
                    credLost += resource.getResourceType().getCredit() * resource.getQuantity();
                    listResourceQuantityDTO.add(
                            ResourceQuantityDTO.builder()
                                    .resourceType(resource.getResourceType())
                                    .quantity(resource.getQuantity())
                                    .build());
                }
            }
        }

        creditsLostDTO.setTotalCreditsLost(credLost);
        creditsLostDTO.setTotalTraitor(totalTraitor);
        creditsLostDTO.setListResourceQuantityDTO(listResourceQuantityDTO);

        return creditsLostDTO;
    }

    @Override
    public List<ResourceCreditDTO> reportResourceCredit() {
        var listResources = new ArrayList<ResourceCreditDTO>();
        for(Enuns.ResourceType resourceType : Enuns.ResourceType.values())
            listResources.add(ResourceCreditDTO.builder()
                    .resourceType(resourceType)
                    .credit(resourceType.getCredit())
                    .build());
        return listResources;
    }
}
