package com.petcare.sistema_petshop.Service;


import com.petcare.sistema_petshop.model.Agendamento;
import com.petcare.sistema_petshop.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public List<Agendamento> listarTodos(){
        return agendamentoRepository.findAll();
    }

    public Agendamento salvar(Agendamento agendamento){
        try {

            if(agendamento.getData().isBefore(LocalDate.now())){            // if para verificar se a hora ou data do agendamento é anterior a de hoje
                throw new RuntimeException("Erro: a data de agendamento ja passou");
            }

            LocalTime abertura = LocalTime.of(8 , 0 );     // crio uma variavel do tipo LocalTime para armazenar a hora que o estabelecimento abre
            LocalTime fechamento = LocalTime.of(18, 0);     // crio uma variavel do tipo LocalTime para armazenar a hora que o estabelicmento encerra
            if(agendamento.getHorario().isBefore(abertura) || agendamento.getHorario().isAfter(fechamento)){     // pergunto se o horario do agendamento foi feito antes da abertura ou feito dps do fechamento
                throw new RuntimeException("Erro: agendamento marcado para fora do horario de serviço (08:00 as 18:00)");
            }

            if(agendamento.getStatus()== null || agendamento.getStatus().isEmpty()){
                agendamento.setStatus("Pendente");
            }

            return agendamentoRepository.save(agendamento);

        }

        catch (Exception e){
            System.out.println("ERRO REAL: " + e.getMessage());
            throw e;
        }




    }

}
