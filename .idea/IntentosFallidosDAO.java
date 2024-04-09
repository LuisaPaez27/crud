/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author luisi
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IntentosFallidosDAO {
    private Connection conexion;
    
    public IntentosFallidosDAO() {
        try {
            conexion = ConexionBD.obtenerConexion();
        } catch (SQLException ex) {
            System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
        }
    }
    
    public void registrarIntentoFallido(IntentoFallido intento) {
        String query = "INSERT INTO intentos_fallidos (usuario, fecha) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, intento.getUsuario());
            ps.setDate(2, new java.sql.Date(intento.getFecha().getTime()));
            ps.executeUpdate();
            System.out.println("Intento fallido registrado correctamente.");
        } catch (SQLException ex) {
            System.out.println("Error al registrar el intento fallido: " + ex.getMessage());
        }
    }
    
    public List<IntentoFallido> obtenerIntentosFallidos(String usuario) {
        List<IntentoFallido> intentos = new ArrayList<>();
        String query = "SELECT id, usuario, fecha FROM intentos_fallidos WHERE usuario = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    IntentoFallido intento = new IntentoFallido();
                    intento.setId(rs.getInt("id"));
                    intento.setUsuario(rs.getString("usuario"));
                    intento.setFecha(rs.getDate("fecha"));
                    intentos.add(intento);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener los intentos fallidos: " + ex.getMessage());
        }
        return intentos;
    }
}

