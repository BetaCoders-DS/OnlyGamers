package com.github.betacoders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

  public static Connection conectar() throws SQLException {
    return DriverManager.getConnection("jdbc:sqlite:onlygamers.db");
  }
}
