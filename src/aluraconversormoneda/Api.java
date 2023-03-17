package aluraconversormoneda;

//https://api.fastforex.io/convert?from=USD&to=PEN&amount=100&api_key=ed5899734b-aa5ccec16a-rrh9ey

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.json.JSONException;
import org.json.JSONObject;

public class Api {
    
    private final String key ="ed5899734b-aa5ccec16a-rrh9ey";
    private final String urlCurrencies ="https://api.fastforex.io/currencies?api_key=";
    private Double rate;
    private Double valorDelCambio;
    
    private String getKey(){
        return this.key;
    }
    
     public String consumirApi (String endPoint) throws IOException{
        StringBuilder jSon = new StringBuilder();
        try {
            URL url = new URL(endPoint);
            jSon.append("");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int codigoDeRespuesta=conn.getResponseCode();
            if(codigoDeRespuesta==200){
                try (Scanner flujo = new Scanner(url.openStream())) {
                    while (flujo.hasNext()) {
                        jSon.append(flujo.nextLine());
                    }
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Error al acceder a los datos. Código " + codigoDeRespuesta);
                alert.showAndWait();
            }
        } catch (IOException e) {
                System.out.println("Error en el consumo de la api: "+ e);
        }
        return jSon.toString();
    }
     
    public void setRate (String from, String to, String monto){
        String url= "https://api.fastforex.io/convert?from=" + from + "&to=" + to + "&amount=" + monto + "&api_key=" + this.getKey();
        try {
            JSONObject registro = new JSONObject(this.consumirApi(url));
            JSONObject montoJSONObject =  registro.getJSONObject("result");
            this.rate = montoJSONObject.getDouble("rate");
        }catch (IOException | JSONException e){
            System.out.println("Error al hacer la conversion del monto"+ e.getMessage());
            this.rate = 0.0;
        }
    }
    
    public Double getRate(){
        return this.rate;
    }
    
    public void setValorDeCambio (String from, String to, String monto){
        String url= "https://api.fastforex.io/convert?from=" + from + "&to=" + to + "&amount=" + monto + "&api_key=" + this.getKey();
        try {
            JSONObject registro = new JSONObject(this.consumirApi(url));
            JSONObject montoJSONObject =  registro.getJSONObject("result");
            System.out.println("paso");
            this.valorDelCambio = montoJSONObject.getDouble(to);
        }catch (IOException | JSONException e){
            System.out.println("Error al hacer la conversion del monto seteando valor de Cambio "+ e.getMessage());
            this.valorDelCambio = 0.0;
        }
    }
    
    public Double getValorDeCambio(){
        return this.valorDelCambio;
    }
    
   

    public ObservableList<String> listaDeMonedas() throws IOException{
        ObservableList<String> items = FXCollections.observableArrayList();
        try {
                JSONObject registro = new JSONObject(this.consumirApi(this.urlCurrencies+this.getKey()));
                JSONObject objMonedas =  registro.getJSONObject("currencies");
                Object[] j =objMonedas.keySet().toArray();
                for (int i=0; i< j.length;i++){
                    items.add(j[i].toString()+" - "+objMonedas.getString(j[i].toString()));
                }
            }catch (IOException | JSONException e){
                System.out.println("Error al generar la lista de monedas"+ e.getMessage());
        }
        return items;
    }
    
    /*
    public String solicitarTipoDeCambio(String from, String to, String monto) throws IOException{
        String url= "https://api.fastforex.io/convert?from=" + from + "&to=" + to + "&amount=" + monto + "&api_key=" + this.getKey();
        String tipoDeCambio = "";
         try {
              JSONObject registro = new JSONObject(this.consumirApi(url));
              JSONObject montoJSONObject =  registro.getJSONObject("result");
              this.setRate(montoJSONObject.getDouble("rate"));
              this.setValorDeCambio(montoJSONObject.getDouble(to));
              tipoDeCambio = "1 " + from + " = " + this.getRate() + " " + to;
            }catch (IOException | JSONException e){
                System.out.println("Error al hacer la conversion del monto"+ e.getMessage());
        }
        return tipoDeCambio;
    }*/
    
}
