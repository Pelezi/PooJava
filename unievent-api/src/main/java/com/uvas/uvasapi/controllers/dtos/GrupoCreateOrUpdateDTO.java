package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.Diretor;
import com.uvas.uvasapi.domain.Endereco;
import com.uvas.uvasapi.domain.Grupo;
import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.services.DiretorService;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GrupoCreateOrUpdateDTO {

    @NotNull(message = "Nome do grupo não informado")
    private String nome;

    private LocalDate dataInauguracao;

    @NotNull(message = "Diretor do grupo não informado")
    private String diretorId;

    private List<Pessoa> integrantesIds = new ArrayList<>();

    private Endereco enderecoId;

    public Grupo getGrupo(DiretorService diretorService){
        Grupo grupo = new Grupo();
        
        grupo.setNome(nome);
        grupo.setDataInauguracao(dataInauguracao);
        Diretor diretor = diretorService.getDiretorById(diretorId);
        grupo.setDiretorId(diretor);
        grupo.setEnderecoId(enderecoId);

        return grupo;
    }
    public Grupo getGrupo(){
        Grupo grupo = new Grupo();

        grupo.setNome(nome);
        grupo.setDataInauguracao(dataInauguracao);
        grupo.setEnderecoId(enderecoId);

        return grupo;
    }

}
