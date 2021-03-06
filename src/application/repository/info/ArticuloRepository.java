package application.repository.info;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.compra.Articulo;
import com.sun.corba.se.impl.encoding.TypeCodeInputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ArticuloRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public void save(Articulo articulo, int indexCategoria){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO ARTICULO values(?,?,?,?,?,?)");
            preparedStatement.setString(1, null);
            preparedStatement.setString(2,articulo.getMarca());
            preparedStatement.setString(3,articulo.getModelo());
            preparedStatement.setString(4,articulo.getDescripcion());
            preparedStatement.setInt(5,indexCategoria);
            preparedStatement.setInt(6,articulo.getStock());
            preparedStatement.executeUpdate();
            String cuerpoMsj = "Artículo '" + articulo.getDescripcion() + "' agregado correctamente.\n";
            Alerta.alertaInfo("Artículos",cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(Articulo articulo, int index){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement(
                    "UPDATE ARTICULO " +
                        "SET Marca=?, Modelo=?, Descripcion=?, CATEGORIA_ARTICULO_idCategoriaArticulo=?, stock=? " +
                    "WHERE idArticulo=?");
            preparedStatement.setString(1,articulo.getMarca());
            preparedStatement.setString(2,articulo.getModelo());
            preparedStatement.setString(3,articulo.getDescripcion());
            preparedStatement.setInt(4,index);
            preparedStatement.setInt(5,articulo.getStock());
            preparedStatement.setInt(6,articulo.getIdArticulo());
            preparedStatement.executeUpdate();
            String headerMsj="Actualización: artículo actualizado";
            String cuerpoMsj = "Artículo: " + articulo.getDescripcion() + " modificado correctamente.";
            Alerta.alertaInfo("Artículos", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(Articulo articulo){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM ARTICULO WHERE idArticulo=?");
            preparedStatement.setInt(1, articulo.getIdArticulo());
            preparedStatement.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public ObservableList<Articulo> view(){
        ObservableList<Articulo> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT a.idArticulo, a.Marca, a.Modelo," +
                    " a.Descripcion, a.stock, ca.NombreCategoria" +
                    "     FROM ARTICULO a, CATEGORIA_ARTICULO ca " +
                    "    WHERE a.CATEGORIA_ARTICULO_idCategoriaArticulo = ca.idCategoriaArticulo ");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Articulo articulo = new Articulo();
                articulo.setIdArticulo(resultSet.getInt(1));
                articulo.setMarca(resultSet.getString(2));
                articulo.setModelo(resultSet.getString(3));
                articulo.setDescripcion(resultSet.getString(4));
                articulo.setStock(resultSet.getInt(5));
                articulo.setCategoria(resultSet.getString(6));
                list.add(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void search(Articulo articulo){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM ARTICULO where idArticulo=?");
            preparedStatement.setInt(1,articulo.getIdArticulo());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
