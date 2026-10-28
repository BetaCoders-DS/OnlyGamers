package com.github.betacoders;

import java.util.List;
import java.util.Optional;

public interface JogadorDAO {
  void salvar(Jogador jogador);

  Optional<Jogador> buscarPorId(int id);

  List<Jogador> listarTodos();

  List<Jogador> listarPorPerfil(Perfil perfil);

  void remover(int id);
}
