package com.github.betacoders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
  public static final String CONNECTION_STR = "jdbc:sqlite:onlygamers.db";

  public static Connection conectar() throws SQLException {
    return DriverManager.getConnection(CONNECTION_STR);
  }
}
