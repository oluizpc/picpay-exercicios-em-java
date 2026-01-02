package picpay_projeto.picpay_project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthorizationService {

    public boolean authorize() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://util.devi.tools/api/v2/authorize";
            Boolean authorized = restTemplate.getForObject(url, Boolean.class);
            return Boolean.TRUE.equals(authorized);
        } catch (Exception e) {
            return false; // Em caso de falha, não autoriza
        }
    }
}
