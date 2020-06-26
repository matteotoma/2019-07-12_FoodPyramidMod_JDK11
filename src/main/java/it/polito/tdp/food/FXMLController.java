package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno B --> switchare al branch master_turnoA per turno A

public class FXMLController {
	
	private Model model;

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtPorzioni;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnAnalisi;

    @FXML
    private Button btnGrassi;

    @FXML
    private Button btnSimula;

    @FXML
    private ComboBox<Food> boxFood;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	Integer numMin;
    	try {
    		numMin = Integer.parseInt(this.txtPorzioni.getText());
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero intero!\n");
    		return;
    	}
    	model.creaGrafo(numMin);
    	this.boxFood.getItems().addAll(model.getFood());
    	txtResult.appendText(String.format("Grafo creato con %d vertici e %d archi!\n", model.nVertici(), model.nArchi()));
    }

    @FXML
    void doGrassi(ActionEvent event) {
    	txtResult.clear();
    	Food source = boxFood.getValue();
    	if(source == null) {
    		txtResult.appendText("Devi selezionare un cibo!\n");
    		return;
    	}
    	List<Adiacenza> result = model.getAdiacenze(source);
    	for(int i=0; i<result.size(); i++) {
    		if(i == 5)
    			return;
    		txtResult.appendText(String.format("%s %s %.2f \n", result.get(i).getF1(), result.get(i).getF2(), result.get(i).getPeso()));
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	Food source = boxFood.getValue();
    	if(source == null) {
    		txtResult.appendText("Devi selezionare un cibo!\n");
    		return;
    	}
    	Integer K;
    	try {
    		K = Integer.parseInt(this.txtK.getText());
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Devi inserire un numero intero!\n");
    		return;
    	}
    	model.simula(source, K);
    	txtResult.appendText(String.format("Sono stati preparati %d cibi in %.2f ore!\n", model.getNumeroCibi(), model.getTempoTotale()));
    }

    @FXML
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnGrassi != null : "fx:id=\"btnGrassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
