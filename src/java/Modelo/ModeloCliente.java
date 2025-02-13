/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Controlador.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jonathan Cabrera
 */
public class ModeloCliente extends Conexion{
        public ArrayList<Cliente> getAllClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM clientes";
            pst = getConexion().prepareCall(sql);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                clientes.add(new Cliente(rs.getInt("id_usuario"), rs.getString("nombre"),rs.getString("pass")));
            }
        } catch (Exception e){
            
        }finally{
            try{
                if(rs != null){
                    rs.close();
                }
                if(pst != null){
                    pst.close();
                }
                if(getConexion()!=null){
                    getConexion().close();
                }
            }catch (Exception e){
                
            }
        }
        return clientes;
    }


    public Cliente getCliente(int id) {
        Cliente cliente = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM clientes where id_usuario =?";
            pst = getConexion().prepareCall(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();

            while (rs.next()) {
                cliente = new Cliente(rs.getInt("id_usuario"), rs.getString("nombre"), rs.getString("pass"));
            }
        } catch (Exception e) {

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (getConexion() != null) {
                    getConexion().close();
                }
            } catch (Exception e) {

            }
        }
        return cliente;
    }


    public Cliente insertarCliente(String usuario, String clave){
        Cliente cliente = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try{
            String insert = "insert into clientes(nombre,pass) values (?,?)";
            System.out.println("Insert es; " + insert);
            pst = getConexion().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, usuario);
            pst.setString(2, clave);
            
            pst.executeUpdate();
            rs=pst.getGeneratedKeys();
            if(rs.next()){
                Integer pk = rs.getInt(1);
                cliente = new Cliente(pk,usuario,clave);
            }
        }catch(Exception e){
            System.out.println("Error en: " + e);
        }finally{
            try {
                if(getConexion()!=null){
                    getConexion().close();
                }
                if(pst!=null) pst.close();
                if(rs!=null) rs.close();

            } catch (Exception e) {
                System.out.println("Error en: " + e);
            }
        }
        return cliente;
    }
    public Cliente actualizarCliente(Cliente cliente) {
        try ( Connection conexion = getConexion();  PreparedStatement pst = conexion.prepareStatement("update clientes set nombre=?, pass=? where id_usuario=?")) {

            pst.setString(1, cliente.getUsuario());
            pst.setString(2, cliente.getClave());
            pst.setInt(3, cliente.getId());

            pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error en: " + e);
        }

        return cliente;
    }


    
    public void eliminarCliente(int id){
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            String delete = "delete from clientes where id_usuario=?";
            System.out.println("delete es;" + delete);
            pst = getConexion().prepareStatement(delete);
            pst.setInt(1,id);
            
            pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en: " + e);
        } finally {
            try {
                if(getConexion()!=null){
                    getConexion().close();
                }
                if(pst!=null) pst.close();
                

            } catch (Exception e) {
                System.out.println("Error en: " + e);
            }

        }
    }
}
