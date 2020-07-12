/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Arco;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	int anno = this.boxAnno.getValue(); 
    	
    	this.model.creaGrafo(anno);
    	this.txtResult.appendText("GRAFO CREATO! \nN VERTICI: "+model.nVertici()+ "\nN ARCHI: "+model.nArchi());
    	
    	this.boxRegista.getItems().clear();
    	this.boxRegista.getItems().addAll(model.registi());

    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	txtResult.clear();
    	Director r = this.boxRegista.getValue(); 
    	
    	txtResult.appendText("\n\nADIACENTI DI "+r);
    	for(Arco a : this.model.getAdiacenti(r)) {
    		txtResult.appendText("\n"+a.getD2()+ " - "+a.getPeso());
    		
    	}

    }

    @FXML
    void doRicorsione(ActionEvent event) {

    	txtResult.clear();
    	Director partenza = this.boxRegista.getValue();
    	int c = Integer.parseInt(this.txtAttoriCondivisi.getText());
    	txtResult.appendText("Cammino massimo di lughezza minore o uguale a "+c+": ");
    	//this.model.cercaCammino(partenza, c);
    	
    	for(Director d: this.model.cercaCammino(partenza, c)) {
    		txtResult.appendText("\n"+d);
    	}
    	
    	txtResult.appendText("\nAttori condivisi totale : "+model.getPesoMin());
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	this.boxAnno.getItems().clear();
    	this.boxAnno.getItems().addAll(model.getAnni());
    	
    	
    }
    
}
