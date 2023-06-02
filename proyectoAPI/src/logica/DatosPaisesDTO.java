package logica;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import persistencia.DatosPaisesDAO;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatosPaisesDTO {
    private static final String BASE_URL = "https://restcountries.com/v3.1/name/";
    private static final String FIELDS = "?fields=name,capital,currencies,gini,population";

    private String nombrePais;
    private String capital;
    private String poblacion;
    private  String gini;

    private JLabel alerta;

    public DatosPaisesDTO(String nombre, String capital, String poblacion, String gini, JLabel alerta){
        this.nombrePais = nombre;
        this.capital = capital;
        this.poblacion = poblacion;
        this.gini = gini;
        this.alerta = alerta;
    }

    public boolean enviarData(){
        DatosPaisesDAO datosPaisesDAO = new DatosPaisesDAO();
        boolean status = datosPaisesDAO.crear(
                this.nombrePais,
                this.capital,
                this.poblacion,
                this.gini
        );

        if(status){
            return true;
        }
        return false;
    }
    public ResultSet traerDatos(){
        DatosPaisesDAO datosPaisesDAO = new DatosPaisesDAO();
        return datosPaisesDAO.listar();
    }
    public void crearFicheros(JTextField nombreArchivo, JComboBox tipoArchivo){
        ResultSet data = this.traerDatos();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        try {
            String nombreFichero = nombreArchivo.getText() + (String) tipoArchivo.getSelectedItem();
            File fichero = new File("C:\\Users\\franc\\OneDrive\\Escritorio\\proyectoAPI", nombreFichero);
            String ficheroLog = nombreArchivo.getText() + ".log";
            File ficheroLogs = new File("C:\\Users\\franc\\OneDrive\\Escritorio\\proyectoAPI", ficheroLog);
            System.out.println(ficheroLog);
            ficheroLogs.createNewFile();
            fichero.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
            BufferedWriter bws = new BufferedWriter(new FileWriter(ficheroLogs));
            while (data.next()) {
                bw.write(data.getString(2) + "\n" + data.getString(3) + "\n" + data.getString(4) + "\n" +data.getString(5) + "\n");
                bws.write("Fecha: " + strDate + "\n" + "Conexion del servidor exitosa\n"
                        + data.getString(2) + "\n" + data.getString(3) + "\n"
                        + data.getString(4) + "\n" +data.getString(5) + "\n"
                        + "Archivo abierto \nArchivo escrito \nArchivo guardado");

            }
            bw.close();
            bws.close();
        }catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public String[] getPaisInfo(String countryName, String ano) throws IOException {
        String formattedCountryName = countryName.replaceAll("\\s+", "%20");
        URL url = new URL(BASE_URL + formattedCountryName + FIELDS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            alerta.setText("El pais solicitado, no existe!!");
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(response.toString());
        System.out.println(json);
        String capital = json.get(0).get("capital").get(0).asText();
        String gini = json.get(0).get("gini").get(ano).asText();
        String poblacion = json.get(0).get("population").asText();
        String nombrePais = String.valueOf(json.get(0).get("name").get("official"));
        String[] datosPais = new String[4];
        datosPais[0] = nombrePais;
        datosPais[1] = gini;
        datosPais[2] = capital;
        datosPais[3] = poblacion;
        return datosPais;
    }
}
