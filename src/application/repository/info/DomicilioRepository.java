	package application.repository.info;

	import application.comunes.Alerta;
	import application.database.JDBCConnection;
	import application.model.info.Domicilio;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	public class DomicilioRepository {
	    Connection connection;
	    PreparedStatement preparedStatement;
	    ResultSet resultSet;
	    
	    public void save(Domicilio domicilio, int localidad){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement("INSERT INTO DOMICILIO (Calle, Numero, LOCALIDAD_idLocalidad) values(?,?,?)");
	            preparedStatement.setString(1,domicilio.getCalle());
	            preparedStatement.setString(2,domicilio.getNumero());
	            preparedStatement.setInt(3,localidad);
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	            String cuerpoMsj = "Domicilio " + domicilio.getCalle() + " " + domicilio.getNumero()+ " agregada correctamente.\n";
	            Alerta.alertaInfo("Domicilio",cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }


	    }
	    public void update(Domicilio domicilio, int localidad){
	        try {
	            connection = JDBCConnection.getInstanceConnection();
	            preparedStatement= connection.prepareStatement("" +
	                    "UPDATE DOMICILIO " +
	                    "SET Calle=?, Numero=?, LOCALIDAD_idLocalidad=? " +
	                    "WHERE idProvincia=?");
	            preparedStatement.setString(1,domicilio.getCalle());
	            preparedStatement.setString(2,domicilio.getNumero());
	            preparedStatement.setInt(3, localidad);
	            preparedStatement.close();
	            connection.close();
	            String headerMsj="Actualización: Domicilio actualizado";
	            String cuerpoMsj = "Domicilio " + domicilio.getCalle() + " " + domicilio.getNumero()+ " agregada correctamente.\n";
	            Alerta.alertaInfo("Domicilio", headerMsj, cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public void delete(Domicilio domicilio){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement(
	                    "DELETE FROM DOMICILIO WHERE idDomicilio=?");
	            preparedStatement.setInt(1, domicilio.getIdDomicilio());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public ObservableList<Domicilio> view(){

	        ObservableList<Domicilio> list = FXCollections.observableArrayList();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT d.Calle, d.Numero, l.NombreLocalidad, p.NombreProvincia "
	            		+ "FROM DOMICILIO d, LOCALIDAD l, PROVINCIA p WHERE d.LOCALIDAD_idLocalidad = l.idLocalidad"
	            		+ " AND l.PROVINCIA_idProvincia = p.idProvincia");
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()){
	                Domicilio domicilio = new Domicilio();
	                domicilio.setIdDomicilio(resultSet.getInt("idDomicilio"));
	                domicilio.setCalle(resultSet.getString("Calle"));
	                domicilio.setNumero(resultSet.getString("Numero"));
	                domicilio.setNombre_localidad(resultSet.getString("NombreLocalidad"));
	                domicilio.setNombre_provincia(resultSet.getString("NombreProvincia"));
	                list.add(domicilio);
	            }
	            preparedStatement.close();
	            resultSet.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	    }
	    public void search(Domicilio domicilio){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT d.Calle, d.Numero, l.NombreLocalidad, p.NombreProvincia "
	            		+ "FROM DOMICILIO d, LOCALIDAD l, PROVINCIA p WHERE d.LOCALIDAD_idLocalidad = l.idLocalidad"
	            		+ " AND l.PROVINCIA_idProvincia = p.idProvincia AND d.idDomicilio=?");
	            preparedStatement.setInt(1,domicilio.getIdDomicilio());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	    }
	}