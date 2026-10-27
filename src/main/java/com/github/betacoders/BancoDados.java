
package com.github.betacoders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;

public class BancoDados {

    public static void inicializar() throws SQLException, IOException {
        try (Connection conexao = DriverManager.getConnection("jdbc:sqlite:onlygamers.db")) {

            boolean tabelaExiste = false;

            try (Statement stmt = conexao.createStatement();
                 ResultSet resultado = stmt.executeQuery(
                     "SELECT name FROM sqlite_master WHERE type='table' AND name='jogador'")) {

                tabelaExiste = resultado.next();
            }

            if (!tabelaExiste) {
                String sql = Files.readString(Path.of("jogadores.sql"));
                String[] comandos = sql.split(";");

                try (Statement stmt = conexao.createStatement()) {
                    for (String comando : comandos) {
                        comando = comando.replaceAll("(?m)--.*$", "").trim();

                        if (!comando.isEmpty()) {
                            stmt.executeUpdate(comando);
                        }
                    }
                }

                System.out.println("Banco criado e jogadores inseridos!");
            } else {
                System.out.println("A tabela jogador já existe.");
            }
        }
    }
}
