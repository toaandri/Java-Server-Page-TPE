package com.projetsimple.taxis.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class Database {
    private static final Properties PROPERTIES = new Properties();
    private static volatile boolean schemaInitialized;

    static {
        try (InputStream in = Database.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new IllegalStateException("db.properties introuvable");
            }
            PROPERTIES.load(in);
            Class.forName("org.postgresql.Driver");
        } catch (IOException | ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private Database() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(
                PROPERTIES.getProperty("db.url"),
                PROPERTIES.getProperty("db.user"),
                PROPERTIES.getProperty("db.password")
        );
        ensureSchema(connection);
        return connection;
    }

    private static void ensureSchema(Connection connection) {
        if (schemaInitialized) {
            return;
        }
        synchronized (Database.class) {
            if (schemaInitialized) {
                return;
            }
            try (InputStream in = Database.class.getClassLoader().getResourceAsStream("sql/schema.sql")) {
                if (in == null) {
                    schemaInitialized = true;
                    return;
                }
                String sql = new String(in.readAllBytes(), StandardCharsets.UTF_8);
                String[] statements = sql.split(";");
                for (String statement : statements) {
                    String trimmed = statement.trim();
                    if (trimmed.isEmpty()) {
                        continue;
                    }
                    try (Statement st = connection.createStatement()) {
                        st.execute(trimmed);
                    }
                }
                schemaInitialized = true;
            } catch (Exception e) {
                throw new RuntimeException("Impossible d'initialiser le schema SQL.", e);
            }
        }
    }
}
