package com.tallerwebi.punta_a_punta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReiniciarDB {

  public static void limpiarBaseDeDatos() {
    try {
      String dbHost = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
      String dbPort = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
      String dbName = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "tallerwebi";
      String dbUser = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "user";
      String dbPassword = System.getenv("DB_PASSWORD") != null
        ? System.getenv("DB_PASSWORD")
        : "user";

      //      String sqlCommands =
      //        "DELETE FROM Usuario;\n" +
      //        "ALTER TABLE Usuario AUTO_INCREMENT = 1;\n" +
      //        "INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);";

      String sqlCommands =
        "SET FOREIGN_KEY_CHECKS = 0; " +
        "DELETE FROM Hijo; " +
        "DELETE FROM Usuario; " +
        "DELETE FROM ItemCarrito; " +
        "DELETE FROM Carrito; " +
        "ALTER TABLE Usuario AUTO_INCREMENT = 1; " +
        "INSERT INTO Usuario(id,dni,nombre,apellido,celular, email, password, rol, activo,fotoPerfil) " +
        "VALUES(null,1234567890, 'Pepe','Sujeto',1112341234,'test@unlam.edu.ar', '$2b$10$rJB22an3TIUZ34l7doj44.FIAeTPMUcqZofxul6GLaQvHn8F0gxi6', 'ADMIN', true,'https://res.cloudinary.com/dqrka5zry/image/upload/v1780520000/istockphoto-1447126543-612x612_ek0kjw.jpg'); " +
        "SET FOREIGN_KEY_CHECKS = 1;";

      //      String comando = String.format(
      //        "docker exec tallerwebi-mysql mysql -h %s -P %s -u %s -p%s %s -e \"%s\"",
      String comando = String.format(
        "docker exec mysql-container mysql -h %s -P %s -u %s -p%s %s -e \"%s\"",
        dbHost,
        dbPort,
        dbUser,
        dbPassword,
        dbName,
        sqlCommands
      );

      //Process process = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", comando });
      String os = System.getProperty("os.name").toLowerCase();
      Process process;

      if (os.contains("win")) {
        process = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", comando });
      } else {
        process = Runtime.getRuntime().exec(new String[] { "/bin/bash", "-c", comando });
      }
      int exitCode = process.waitFor();

      BufferedReader errorReader = new BufferedReader(
        new InputStreamReader(process.getErrorStream())
      );

      String linea;
      while ((linea = errorReader.readLine()) != null) {
        System.out.println("ERROR: " + linea);
      }

      if (exitCode == 0) {
        System.out.println("Base de datos limpiada exitosamente");
      } else {
        System.err.println("Error al limpiar la base de datos. Exit code: " + exitCode);
      }
    } catch (IOException | InterruptedException e) {
      System.err.println("Error ejecutando script de limpieza: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
