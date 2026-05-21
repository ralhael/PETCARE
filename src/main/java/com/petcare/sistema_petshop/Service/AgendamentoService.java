package com.petcare.sistema_petshop.Service;


import com.petcare.sistema_petshop.model.Agendamento;
import com.petcare.sistema_petshop.model.Funcionario;
import com.petcare.sistema_petshop.model.Servico;
import com.petcare.sistema_petshop.repository.AgendamentoRepository;
import com.petcare.sistema_petshop.repository.FuncionarioRepository;
import com.petcare.sistema_petshop.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    public List<Agendamento> listarTodos(){
        return agendamentoRepository.findAll();
    }

    public Agendamento salvar(Agendamento agendamento){
        try {

            // 1. Validação de data retroativa
            if(agendamento.getData().isBefore(LocalDate.now())){            // if para verificar se a hora ou data do agendamento é anterior a de hoje
                throw new RuntimeException("Erro: a data de agendamento ja passou");
            }

            // 2. Validação do horário de funcionamento do Petshop
            LocalTime abertura = LocalTime.of(8 , 0 );     // crio uma variavel do tipo LocalTime para armazenar a hora que o estabelecimento abre
            LocalTime fechamento = LocalTime.of(18, 0);     // crio uma variavel do tipo LocalTime para armazenar a hora que o estabelicmento encerra
            if(agendamento.getHorario().isBefore(abertura) || agendamento.getHorario().isAfter(fechamento)){     // pergunto se o horario do agendamento foi feito antes da abertura ou feito dps do fechamento
                throw new RuntimeException("Erro: agendamento marcado para fora do horario de serviço (08:00 as 18:00)");
            }

            // 3. Definição do status padrão caso venha vazio
            if(agendamento.getStatus()== null || agendamento.getStatus().isEmpty()){
                agendamento.setStatus("Pendente");
            }

            // 4. Validação e amarração do Serviço (Garantir que o serviço selecionado existe)
            if (agendamento.getServico() != null && agendamento.getServico().getId() != null) {
                Servico servicoBanco = servicoRepository.findById(agendamento.getServico().getId())
                        .orElseThrow(() -> new RuntimeException("Erro: Serviço não encontrado"));
                agendamento.setServico(servicoBanco);
            }



            // Pergunta ao banco quais funcionários não estão trabalhando nesta data e horário
            List<Funcionario> funcionariosLivres = funcionarioRepository.findFuncionariosLivres(
                    agendamento.getData(),
                    agendamento.getHorario()
            );

            // Se a lista voltar vazia, significa que TODOS os funcionários cadastrados já estão ocupados nesse horário
            if (funcionariosLivres.isEmpty()) {
                throw new RuntimeException("Erro: Não há funcionários disponíveis para este dia e horário.");
            }

            // Se houver alguém livre, pega o primeiro profissional disponível da lista (posição 0)
            Funcionario funcionarioEscolhido = funcionariosLivres.get(0);

            // Vincula o funcionário sorteado/disponível ao agendamento
            agendamento.setFuncionario(funcionarioEscolhido);

            // =========================================================================================

            // Salva o agendamento blindado no banco de dados
            return agendamentoRepository.save(agendamento);

        }
        catch (Exception e){
            System.out.println("ERRO REAL: " + e.getMessage());
            throw e;
        }
    }

}
