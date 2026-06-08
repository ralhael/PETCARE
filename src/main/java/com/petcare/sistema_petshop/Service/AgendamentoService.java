package com.petcare.sistema_petshop.Service;


import com.petcare.sistema_petshop.dto.AgendamentoRequestDTO;
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

    public Agendamento salvar(AgendamentoRequestDTO dados){
        try {

            // validar se a data ja passou
            if(dados.data().isBefore(LocalDate.now())){
                throw new RuntimeException("Erro: a data de agendamento ja passou");
            }

            // validar se ta fora de funcionamento
            LocalTime abertura = LocalTime.of(8 , 0 );
            LocalTime fechamento = LocalTime.of(18, 0);
            if(dados.hora().isBefore(abertura) || dados.hora().isAfter(fechamento)){
                throw new RuntimeException("Erro: agendamento marcado para fora do horario de serviço (08:00 as 18:00)");
            }

            // verifica se o servico existe
            Servico servicoBanco = servicoRepository.findById(dados.servicoId())
                    .orElseThrow(() -> new RuntimeException("Erro: Serviço não encontrado"));

            // verifica se o cliente existe
            Cliente clienteBanco = clienteRepository.findById(dados.clienteId())
                    .orElseThrow(() -> new RuntimeException("Erro: Cliente não encontrado no banco de dados "));

            // verifica se ela existe ou se ja tem horario marcado p aql hora
            Animal animalBanco = animalRepository.findById(dados.animalId())
                    .orElseThrow(() -> new RuntimeException("Erro: Animal não encontrado no banco de dados"));

            boolean animalOcupado = agendamentoRepository.existsByAnimalIdAndDataAndHorario(
                    dados.animalId(),
                    dados.data(),
                    dados.hora()
            );
            if (animalOcupado){
                throw new RuntimeException("Erro: Este animal já possui um agendamento marcado para este mesmo dia e horário.");
            }

            // verificacao dos funcionarios livres
            List<Funcionario> funcionariosLivres = funcionarioRepository.findFuncionariosLivres(
                    dados.data(),
                    dados.hora()
            );

            // Se a lista voltar vazia, significa que TODOS os funcionários cadastrados já estão ocupados nesse horário
            if (funcionariosLivres.isEmpty()) {
                throw new RuntimeException("Erro: Não há funcionários disponíveis para este dia e horário.");
            }

            // Se houver alguém livre, pega o primeiro profissional disponível da lista (posição 0)
            Funcionario funcionarioEscolhido = funcionariosLivres.get(0);


            // 7. Instanciação e montagem da nossa Model real (Entidade) com os objetos que validamos
            Agendamento novoAgendamento = new Agendamento();
            novoAgendamento.setData(dados.data());
            novoAgendamento.setHorario(dados.hora()); // Mapeado com o setHorario da sua Model

            // Define o status: usa o do DTO se vier preenchido, senão joga "Pendente"
            if (dados.status() == null || dados.status().isEmpty()) {
                novoAgendamento.setStatus("Pendente");
            } else {
                novoAgendamento.setStatus(dados.status());
            }

            novoAgendamento.setServico(servicoBanco);
            novoAgendamento.setCliente(clienteBanco);
            novoAgendamento.setAnimal(animalBanco);
            novoAgendamento.setFuncionario(funcionarioEscolhido);

            // Salva o agendamento blindado no banco de dados
            return agendamentoRepository.save(novoAgendamento);

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

    // metodo para poder "cancelar" ( vou mudar apenas o status dele para cancelado
    public void cancelarAgendamento(Long id){           // cria metodo que nao retorna nada e recebe de parametro o Id do agendamento q ta procurando
        Agendamento agendamento = agendamentoRepository.findById(id)  // aqui eu crio a variavel agendamento para salvar nela qnd reposiroty buscar pelo id lido
                .orElseThrow(() -> new RuntimeException("Erro: Agendamento nao encontrado"));   // caso nao encontre
        agendamento.setStatus("Cancelado");                                                     // encontrou entao muda o Status do agendamento para Cancelado
        agendamentoRepository.save(agendamento);                                                // salva o agendamento
    }

    public List<Agendamento> buscarPorData(LocalDate data){         // vai retornar lista de datas chamando metodo da repository q busca as datas
        return agendamentoRepository.findByData(data);
    }

    public void atualizarStatus(Long id , String novoStatus){
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Erro : Agendamento nao encontrado"));

        String statusLimpo = novoStatus.replace("\"", "").trim();
        agendamento.setStatus(statusLimpo);
        agendamentoRepository.save(agendamento);
    }



}
