package com.petcare.sistema_petshop.Service;


import com.petcare.sistema_petshop.model.*;
import com.petcare.sistema_petshop.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AnimalRepository animalRepository;

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

            // validar se existe o cliente
            if(agendamento.getCliente()!= null && agendamento.getCliente().getId()!= null){         // verifica se o cliente e o id do cliente veio diferente de nulo
                Cliente clienteBanco = clienteRepository.findById(agendamento.getCliente().getId())  // busca no banco o cliente com o Id é oq ele recebeu
                        .orElseThrow(() -> new RuntimeException("Erro: Cliente não encontrado no banco de dados "));  // se nao , manda mensagem de erro dizendo que nao foi encontrado no banco
                agendamento.setCliente(clienteBanco);  // pega o cliente buscado e coloca no agendamento
            }

            //evitar agendamento duplicado(mesmo dia mesmo horario)
            if(agendamento.getAnimal()!= null&&agendamento.getAnimal().getId()!= null){           // verifico se o animal ja existe no banco
                Animal animalBanco = animalRepository.findById(agendamento.getAnimal().getId())
                        .orElseThrow(() -> new RuntimeException("Erro: Animal não encontrado no banco de dados"));
                agendamento.setAnimal(animalBanco);
                boolean animalOcupado = agendamentoRepository.existsByAnimalIdAndDataAndHorario(    // salvo em animal ocupado(boolean ent vai retornar true se o metodo da repository disser que ja existe aql id nql horario e dia)
                        agendamento.getAnimal().getId(),        // os parametros do metodo q criei no agendamentoRepository
                        agendamento.getData(),
                        agendamento.getHorario()
                );
                if (animalOcupado){                             // se der true retorna esse erro ai
                    throw new RuntimeException("Erro: Este animal já possui um agendamento marcado para este mesmo dia e horário.");
                }
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

    public Agendamento atualizarAgendamento(Long id , Agendamento dadosNovos){
        Agendamento agendamentoBanco = agendamentoRepository.findById(id)       // verifica se o id existe no banco
                .orElseThrow(() -> new RuntimeException("Erro: Agendamento nao encontrado"));  // caso nao existir
        BeanUtils.copyProperties(dadosNovos, agendamentoBanco, getNullPropertyNames(dadosNovos));       // chama esse metodo do Spring que copia dados de um objeto p/ outro
        // (1 campo oq ele vai ler , 2 campo para onde ele vai colocar oq ele leu, terceiro campo o metodo que criei para ele ignora oq nao esta escrito e assim nao sobreescreve
        return agendamentoRepository.save(agendamentoBanco);                // salva o agendamento atualizado

    }

    // metodo que serve para verificar os campos nulos q vieram no Json
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            // Se o campo for nulo, adiciona ele na lista de "ignorar"
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    // metodo para buscar o cliente puxando para o metodo la na repository ( uma ponte )
    public List<Agendamento> buscarPorCliente(Long clienteId){
        return agendamentoRepository.findByClienteId(clienteId);
    }

}
