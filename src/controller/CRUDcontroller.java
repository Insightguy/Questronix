package controller;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DBUtility;

@Path("/CRUD")
public class CRUDcontroller {
	
	private Connection conn = DBUtility.getConnection();

	@GET
	@Path("/Get")
	@Produces(MediaType.TEXT_HTML)
	public String SeeThings() {
		
		String WholeTable;
		WholeTable = "<table>" + 
				"		<thead>" + 
				"			<tr>" + 
				"				<th>ID</th>" + 
				"				<th>Name</th>" + 
				"				<th>Age</th>" + 
				"			</tr>" + 
				"		</thead>" + 
				"		<tbody>";
		
		try {
			PreparedStatement preparedStatement = conn.prepareStatement( "SELECT * FROM test" );
			ResultSet resultSet = preparedStatement.executeQuery();
			while( resultSet.next() ) {
				WholeTable += "		<tr>" +
						"				<td>" + resultSet.getInt( "ID" ) + "</td>" + 
						"				<td>" + resultSet.getString( "Name" ) + "</td>" + 
						"				<td>" + resultSet.getInt( "Age" ) + "</td>" + 
						"			</tr>";
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		WholeTable +="	</tbody>" + 
				"	</table>";
		return WholeTable;
	}
	
	@PUT
	@Path("/Put")
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.TEXT_HTML)
	public String addThings(@QueryParam("Name") String name, @QueryParam("Age") int age) {
		
		String Insersion = "";
		try (PreparedStatement stmt = conn.prepareStatement( "INSERT INTO test (Name, Age) VALUES (?,?)" );){
			stmt.setString( 1, name );
			stmt.setInt( 2, age );
			stmt.executeUpdate();
			stmt.close();
			Insersion += "<h1>A new entry has been added with Name: " + name + " and Age: " + age + "</h1>";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Insersion += "<h1>asdf</h1>";
		return Insersion;
	}
	
	@DELETE
	@Path("/Delete")
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.TEXT_HTML)
	public String removeThings(@QueryParam("ID") int ID) {
		
		String Insersion = "";
		try (PreparedStatement stmt = conn.prepareStatement( "DELETE FROM test WHERE ID = ?" );){
			stmt.setInt( 1, ID );
			stmt.executeUpdate();
			stmt.close();
			Insersion += "<h1>Entry Number: " + ID + " Has been wiped</h1>";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Insersion += "<h1>asdf</h1>";
		return Insersion;
	}
	

}
