package picpay_projeto.picpay_project.dto;

import picpay_projeto.picpay_project.enums.TipoUsuario;

public record UserUpdateDTO(String nomeCompleto, String email, String senha, TipoUsuario tipoUsuario) {}
