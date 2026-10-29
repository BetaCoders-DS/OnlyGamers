
package com.github.betacoders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;

public class BancoDados {
  public static final String PLAYERS_TABLE = "jogador";
  public static final String PLAYERS_SQL_FILE = "jogadores.sql";

  public static void inicializar() throws SQLException, IOException {
    try (Connection conexao = Conexao.conectar()) {

      boolean tabelaExiste = false;

      try (
          Statement stmt = conexao.createStatement();
          ResultSet resultado = stmt.executeQuery(
              String.format("SELECT name FROM sqlite_master WHERE type='table' AND name='%s'",
                  PLAYERS_TABLE))) {

        tabelaExiste = resultado.next();
      }

      if (tabelaExiste) {
        System.out.println("A tabela jogador já existe.");
        return;
      }

      String sql = Files.readString(Path.of(PLAYERS_SQL_FILE));
      String[] comandos = sql.split(";");

      try (Statement stmt = conexao.createStatement()) {
        for (String comando : comandos) {
          comando = comando.replaceAll("(?m)--.*$", "").trim();

          if (comando.isEmpty())
            continue;

          stmt.executeUpdate(comando);
        }
      }

      System.out.println("Banco criado e jogadores inseridos!");
    }
  }
}
