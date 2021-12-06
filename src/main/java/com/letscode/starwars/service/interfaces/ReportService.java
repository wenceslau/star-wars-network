package com.letscode.starwars.service.interfaces;


import com.letscode.starwars.model.dto.AverageResourcesDTO;
import com.letscode.starwars.model.dto.CreditsLostDTO;
import com.letscode.starwars.model.dto.PercentTraitorsDTO;
import com.letscode.starwars.model.dto.PercentsRebelDTO;
import com.letscode.starwars.model.dto.ResourceCreditDTO;

import java.util.List;

public interface ReportService {

    public PercentTraitorsDTO reportPercentTraitor();

    public PercentsRebelDTO reportPercentRebel();

    public AverageResourcesDTO reportAverageResources();

    public CreditsLostDTO reportCreditLost();

    public List<ResourceCreditDTO> reportResourceCredit();

}
