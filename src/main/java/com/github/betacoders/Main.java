
package com.github.betacoders;

import java.sql.Connection;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    try {
      BancoDados.inicializar();

      try (Connection conexao = Conexao.conectar()) {
        JogadorDAO dao = new JogadorDAOJdbc(conexao);

        List<Jogador> jogadores = dao.listarTodos();

        System.out.println("Total de jogadores: " + jogadores.size());
        System.out.println();

        for (Jogador jogador : jogadores) {
          System.out.println(jogador);
        }
      }

    } catch (SQLException | IOException e) {
      System.out.println("Erro ao inicializar o banco: " + e.getMessage());
    }
  }
}
