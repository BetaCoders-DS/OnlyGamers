package com.github.betacoders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BancoDados {
  public static final String PLAYERS_TABLE = "jogador";
  public static final String PLAYERS_SQL_FILE = "jogadores.sql";

  public static void inicializar() throws SQLException, IOException {
    try (Connection conexao = Conexao.conectar()) {
      if (tabelaExiste(conexao, PLAYERS_TABLE)) {
        System.out.println("A tabela jogador já existe.");
        return;
      }

      executarScriptSql(conexao);
      System.out.println("Banco criado e jogadores inseridos!");
    }
  }

  private static boolean tabelaExiste(Connection conexao, String table) throws SQLException {
    String sql = String.format(
        "SELECT name FROM sqlite_master WHERE type='table' AND name='%s'",
        table);

    try (Statement stmt = conexao.createStatement();
        ResultSet resultado = stmt.executeQuery(sql)) {
      return resultado.next();
    }
  }

  private static void executarScriptSql(Connection conexao) throws IOException, SQLException {
    String sql = lerSqlSemComentarios(PLAYERS_SQL_FILE);
    String[] comandos = sql.split(";");

    try (Statement stmt = conexao.createStatement()) {
      for (String comando : comandos) {
        String cmd = comando.trim();
        if (cmd.isEmpty()) {
          continue;
        }
        stmt.executeUpdate(cmd);
      }
    }
  }

  private static String lerSqlSemComentarios(String arquivo) throws IOException {
    String sql = Files.readString(Path.of(arquivo));
    return sql.replaceAll("(?m)--.*$", "");
  }
}
