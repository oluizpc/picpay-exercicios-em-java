package picpay_projeto.picpay_project.dto;

public record AuthorizationResponseDTO(
        String status,
        Data data
) {
    public record Data(boolean authorization) {}
}