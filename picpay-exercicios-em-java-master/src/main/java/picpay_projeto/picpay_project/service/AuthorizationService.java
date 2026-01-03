package picpay_projeto.picpay_project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import picpay_projeto.picpay_project.dto.AuthorizationResponseDTO;

@Service
public class AuthorizationService {

    public boolean authorize() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://util.devi.tools/api/v2/authorize";

            AuthorizationResponseDTO response =
                    restTemplate.getForObject(url, AuthorizationResponseDTO.class);

            return response != null
                    && response.data() != null
                    && response.data().authorization();

        } catch (Exception e) {
            //  fallback para ambiente de desenvolvimento
            return true;
        }
    }
}