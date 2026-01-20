package com.deployrest.rest_prova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deployrest.rest_prova.model.CloudVendor;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import javax.sql.DataSource;

@RestController
@RequestMapping("/cloudvendor")

public class CloudAPIService {
	
	
	@Autowired
    private DataSource dataSource;
	
	@PostMapping("/login")
	public String login(@RequestBody CloudVendor cloudvendor  ) 
	{
		
		String allo = ""; 
			try (Connection con = dataSource.getConnection()) {
				
				String email = cloudvendor.getEmail();
				String password = cloudvendor.getPassword();
				
				String query = "SELECT * FROM utenti WHERE email = ? AND password = ?";
				
				java.sql.PreparedStatement pstmt = con.prepareStatement(query);
				
				 pstmt.setString(1, email);
	             pstmt.setString(2, password);
	             
	            ResultSet rs = pstmt.executeQuery();
	            
	            if (rs.next()) {
	            	System.out.println("Sei dentro");
	            	allo = rs.getString("nome");
	            }
	            else {
	            	System.out.println("Credenziali errate");
	            	System.out.println(email);
	            	System.out.println(password);
	            }
				pstmt.close();
			}
			catch (Exception e) {
			System.out.println("Connection error" + e);
		}
		return "Benvenuto utente: " + allo;
			
	}
	@PostMapping("/registrazione")
	public String Registrazione(@RequestBody CloudVendor cloudVendor) {
		String allo = cloudVendor.getNome();
				try (Connection con = dataSource.getConnection()){
					
				String nome = cloudVendor.getNome();
				String cognome = cloudVendor.getCognome();
				String email = cloudVendor.getEmail();
				String password = cloudVendor.getPassword();
				
				URL url = new URL("https://cataas.com/cat");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				
				InputStream inputStream = connection.getInputStream();
				
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                
                byte[] immagineInByte = outputStream.toByteArray();
                
				String b64 = Base64.getEncoder().encodeToString(immagineInByte);

				String query = "INSERT INTO utenti (nome , cognome  , email  , password , immagini) values ( ? , ? , ? , ?, ?)";
				
				java.sql.PreparedStatement pstmt = con.prepareStatement(query);
				
				pstmt.setString(1, nome); 
				pstmt.setString(2, cognome);
				pstmt.setString(3, email);
	            pstmt.setString(4, password);
	            pstmt.setString(5, b64);
	            
	            pstmt.executeUpdate();

	            pstmt.close();
			}
			catch (Exception e) {
			System.out.println("Connection error" + e);

		}
		return "Utente creato con successo: " + allo;
		
	}
	
	@PostMapping("/delete")
	public String Eliminazione(@RequestBody CloudVendor Cloudvendor) {
		String allo = ""; 
			try (Connection con = dataSource.getConnection()){
				
				String email = Cloudvendor.getEmail();
				String password = Cloudvendor.getPassword();
	
				String query = "DELETE FROM utenti WHERE email = ? and password = ?";
				
				java.sql.PreparedStatement pstmt = con.prepareStatement(query);
				
				pstmt.setString(1, email);
	            pstmt.setString(2, password);
	            
	            pstmt.executeUpdate();
				
	            pstmt.close();
			}
			catch (Exception e) {
			System.out.println("Connection error" + e);
		}
		return "Utente eliminato con successo " ;
		
	}
	
	@PostMapping("/inviocsv")
    public String Filecsv(@RequestParam("file") MultipartFile csv) {
        int i = 0;
        try (Connection con = dataSource.getConnection()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(csv.getInputStream()));
            String line;
            String query = "INSERT INTO utenti (nome , cognome  , email  , password, immagini ) values ( ? , ? , ? , ?, ?)";

            // OTTIMIZZAZIONE: Prepariamo la query UNA volta sola fuori dal ciclo
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");

                    String nome = values[0];
                    String cognome = values[1];
                    String email = values[2];
                    String password = values[3];

                    // Scarica immagine gatto
                    URL url = new URL("https://cataas.com/cat");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    byte[] immagineInByte = outputStream.toByteArray();
                    String b64 = Base64.getEncoder().encodeToString(immagineInByte);

                    // Imposta i parametri e riesegue la query preparata
                    pstmt.setString(1, nome);
                    pstmt.setString(2, cognome);
                    pstmt.setString(3, email);
                    pstmt.setString(4, password);
                    pstmt.setString(5, b64);

                    pstmt.executeUpdate();
                    i += 1;
                }
            } // Qui si chiude il PreparedStatement

        } catch (Exception e) {
            System.out.println("Connection error: " + e);
            e.printStackTrace();
        }
        return "Utenti aggiunti con successo: " + i;
    }
}

