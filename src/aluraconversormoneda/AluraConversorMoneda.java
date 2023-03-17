/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aluraconversormoneda;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Paul
 */
public class AluraConversorMoneda extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        //CREANDO ELEMENTOS (NODOS)
        
        //Añadiendo Imagen de conversión 
        Image img = new Image(getClass().getResourceAsStream("/resources/hombre-negocios-barbudo-alegre-camisa-dinero.jpg"));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(200);
        imgView.setFitWidth(200);
        
        Circle clipShape = new Circle();
        clipShape.setCenterX(100);
        clipShape.setCenterY(100);
        clipShape.setRadius(100);
        
        imgView.setClip(clipShape);
        
        Label lblMontoACambiar = new Label();
        lblMontoACambiar.setText("MONTO A CAMBIAR");
        lblMontoACambiar.setTextFill(Color.rgb(255, 255, 255));
        
        TextField txfMontoACambiar = new TextField();
                        
        Label lblDe = new Label();
        lblDe.setText("DE");
        lblDe.setTextFill(Color.rgb(255, 255, 255));
        
        //Creando ComboBox Moneda Destino para la conversión yLabel 2
        Label lblA = new Label();
        lblA.setText("A");
        lblA.setTextFill(Color.rgb(255, 255, 255));
        
        Label lblCambioDeDivisas = new Label();
        lblCambioDeDivisas.setText("Valor de Cambio");
        lblCambioDeDivisas.setTextFill(Color.rgb(255, 255, 255));
        
        Label lblValorDeCambio = new Label();  //Label para saber el tipo de cambio Ej. 1PEN = 0.27USD
        lblValorDeCambio.setTextFill(Color.rgb(255, 255, 255));
        
        Label montoCambiado = new Label();
        montoCambiado.setTextFill(Color.rgb(255, 255, 255));
        montoCambiado.setFont(Font.font(32));
        montoCambiado.setText("0");
        
        Button btnCambiar = new Button();
        btnCambiar.setText("CAMBIAR");
        btnCambiar.setMinWidth(150);
        btnCambiar.setMinHeight(30);
        btnCambiar.setFont(Font.font(16));
        btnCambiar.setStyle("-fx-background-color: #ffffff; ");
        btnCambiar.setStyle("-fx-text-fill: #B00020");
                
        //Creando ComboBoxMonedaOrigen y ComoBoxDestino para la conversión
        //Trayendo datos de la API
        ObservableList<String> items = FXCollections.observableArrayList();
        Api api = new Api();
        try {
            items = api.listaDeMonedas();
        } catch (IOException ex) {
            Logger.getLogger(AluraConversorMoneda.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Cargando los datos traidos de la API a los comboBox
        ComboBox<String> cbxOrigen = new ComboBox<>(items);
        ComboBox<String> cbxDestino = new ComboBox<>(items);
        cbxOrigen.setMaxWidth(100);
        cbxDestino.setMaxWidth(100);
        cbxOrigen.setValue("USD");
        cbxDestino.setValue("PEN");

        //Primera carga del lblValorDeCambio
        api.setRate(cbxOrigen.getValue().substring(0, 3), cbxDestino.getValue().substring(0, 3), "1");
        lblValorDeCambio.setText("1 "+ cbxOrigen.getValue().substring(0, 3) + " = " + api.getRate() + " " + cbxDestino.getValue().substring(0,3));
        
        //Actualizacion del tipo de cambio y del valor cambiado
        cbxOrigen.getSelectionModel().selectedItemProperty().addListener(( ov, t,  t1) -> {
            api.setRate(cbxOrigen.getValue().substring(0, 3), cbxDestino.getValue().substring(0, 3), "1");
            montoCambiado.setText("0");
            lblValorDeCambio.setText("1 "+ cbxOrigen.getValue().substring(0, 3) + " = " + api.getRate() + " " + cbxDestino.getValue().substring(0,3));
        });
        cbxDestino.getSelectionModel().selectedItemProperty().addListener(( ov, t,  t1) -> {
            api.setRate(cbxOrigen.getValue().substring(0, 3), cbxDestino.getValue().substring(0, 3), "1");
            montoCambiado.setText("0");
            lblValorDeCambio.setText("1 "+ cbxOrigen.getValue().substring(0, 3) + " = " + api.getRate() + " " + cbxDestino.getValue().substring(0,3));
        });
        
        /*Validación del monto a cambiar  Double monto = Double.parseDouble(lblMontoACambiar.getText()); */
        txfMontoACambiar.setOnKeyTyped(event ->{
            boolean tienePunto = false;
            SoloNumerosEnteros(event, txfMontoACambiar.getText().contains("."));
            montoCambiado.setText("0");
        });
        
        btnCambiar.setOnAction(event ->{
            api.setValorDeCambio(cbxOrigen.getValue().substring(0, 3), cbxDestino.getValue().substring(0, 3), txfMontoACambiar.getText());
            montoCambiado.setText(api.getValorDeCambio().toString());
        });
               
        //Creando Nodo Raiz
        AnchorPane nodoRaiz = new AnchorPane();
        
        //Creando escena
        Scene scene = new Scene (nodoRaiz, 600,520);
        
        //Posicionando los children
        AnchorPane.setTopAnchor(imgView, 20.0);
        AnchorPane.setLeftAnchor(imgView, (scene.getWidth()- imgView.getFitWidth())/2);
        
        AnchorPane.setTopAnchor(lblMontoACambiar, 250.0);
        lblMontoACambiar.setPrefWidth(scene.getWidth());
        lblMontoACambiar.setAlignment(Pos.CENTER);
        
        AnchorPane.setTopAnchor(txfMontoACambiar, 280.0);
        txfMontoACambiar.setPrefWidth(150);
        AnchorPane.setLeftAnchor(txfMontoACambiar, (scene.getWidth()-150)/2);
        txfMontoACambiar.setPrefWidth(150);
        txfMontoACambiar.setAlignment(Pos.CENTER);
        
        AnchorPane.setTopAnchor(lblDe, 325.0);
        AnchorPane.setLeftAnchor(lblDe, 150.0);
        AnchorPane.setTopAnchor(cbxOrigen, 320.0);
        AnchorPane.setLeftAnchor(cbxOrigen, 180.0);
        
        AnchorPane.setTopAnchor(lblA, 325.0);
        AnchorPane.setRightAnchor(lblA, 280.0);
        AnchorPane.setTopAnchor(cbxDestino, 320.0);
        AnchorPane.setRightAnchor(cbxDestino, 180.0);
        
        AnchorPane.setTopAnchor(lblCambioDeDivisas, 360.0);
        lblCambioDeDivisas.setPrefWidth(scene.getWidth());
        lblCambioDeDivisas.setAlignment(Pos.CENTER);
        
        AnchorPane.setTopAnchor(lblValorDeCambio, 380.0);
        lblValorDeCambio.setPrefWidth(scene.getWidth());
        lblValorDeCambio.setAlignment(Pos.CENTER);
        
        AnchorPane.setTopAnchor(btnCambiar, 410.0);
        AnchorPane.setLeftAnchor(btnCambiar, (scene.getWidth()-btnCambiar.getMinWidth())/2);
        btnCambiar.setAlignment(Pos.CENTER);
        
        AnchorPane.setTopAnchor(montoCambiado, 450.0);
        montoCambiado.setPrefWidth(scene.getWidth());
        montoCambiado.setAlignment(Pos.CENTER);
        
        //Añadiendo elementos al nodo Raiz
        nodoRaiz.getChildren().add(imgView);
        nodoRaiz.getChildren().add(lblMontoACambiar);
        nodoRaiz.getChildren().add(txfMontoACambiar);
        nodoRaiz.getChildren().add(lblCambioDeDivisas);
        nodoRaiz.getChildren().add(lblA);
        nodoRaiz.getChildren().add(cbxOrigen);
        nodoRaiz.getChildren().add(lblDe);
        nodoRaiz.getChildren().add(cbxDestino);
        nodoRaiz.getChildren().add(lblValorDeCambio);
        nodoRaiz.getChildren().add(montoCambiado);
        nodoRaiz.getChildren().add(btnCambiar);
        nodoRaiz.setStyle("-fx-background-color:#B00020");
      
        //Pasando escena a la ventana (Stage)
        primaryStage.setTitle("Alura Conversor de Monedas");
        primaryStage.setScene(scene);
       
        //Mostrando la ventana
        primaryStage.show();
             
    }   
    public static void main(String[] args) {
        launch(args);
    }
    
    //Validación de monto para convertir ingresado, solo numeros y un punto decimal.
    public void SoloNumerosEnteros(javafx.scene.input.KeyEvent keyEvent, boolean hayPunto) {
        try{
            char key = keyEvent.getCharacter().charAt(0);
            if (!Character.isDigit(key)){
                if (key=='.' && hayPunto==false){
                    hayPunto=true;
                }else{
                    keyEvent.consume();
                }
            }
        } catch (Exception ex){ }
    }
    
}
