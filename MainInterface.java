import java.util.List;

import org.kordamp.bootstrapfx.scene.layout.Panel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainInterface {
	private String[] dateArr;
	private int[] totalInfectedCaseArr;
	private int[] totalRecoveryCaseArr;
	private int[] totalDeathCaseArr;
	
	private int[] newlyDiscoveredArr;
	private int[] newlyRecoveredArr;
	private int[] newlyDeathArr;
	
	private City city;
	
	public Stage showMainInterface() {
		
		
		Stage primaryStage = new Stage();
		Panel panel = new Panel("COVID-19 Simulation");
        
        panel.getStyleClass().add("panel-primary");                           
        GridPane content = new GridPane();
        
       
        
        
        Text name = new Text("Enter the name of the city: ");
        TextField nameOfCity = new TextField();
        HBox hbox1 = new HBox();
        hbox1.getChildren().addAll(name, nameOfCity);

        Text days = new Text("Number of days to simulate: ");
        TextField numberOfDays = new TextField();
        numberOfDays.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numberOfDays.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        HBox hbox2 = new HBox();
        hbox2.getChildren().addAll(days, numberOfDays);

        
        ComboBox<String> withContactTracer = new ComboBox<>();
        withContactTracer.getItems().addAll("Yes", "No");
        withContactTracer.setValue("No");
        HBox hbox3 = new HBox();
        Text contactTracerText = new Text("Contact Tracer:\t");
        hbox3.getChildren().addAll(contactTracerText, withContactTracer);

        ComboBox<String> isMco = new ComboBox<>();
        isMco.getItems().addAll("Yes", "No");
        isMco.setValue("No");
        Text mcoText = new Text("Movement Control Order(MCO):\t");
        HBox hbox4 = new HBox();
        hbox4.getChildren().addAll(mcoText, isMco);
        
        Button createCityButton = new Button("Start Simulation");
        createCityButton.setAlignment(Pos.BASELINE_CENTER);
        createCityButton.getStyleClass().setAll("btn","btn-danger");

        createCityButton.setPrefSize(200, 50);
        
        createCityButton.setOnAction(e -> {
        	
        	// starting to simulate based on the condition applied
        	try {
        		city = new City();
        		city.generateCity(nameOfCity.getText());
    			List<Person> listOfPerson = city.getHumanArr();
    			List<Building> listOfBuilding = city.getBuildingArr();
        	
    			ActivitySimulator ac = new ActivitySimulator();    	
    			boolean onContactTracer = withContactTracer.getValue().equals("Yes");
    			boolean onMco = isMco.getValue().equals("Yes");
				ac.simulate(listOfPerson, listOfBuilding, Integer.parseInt(numberOfDays.getText()), onContactTracer, onContactTracer, onMco);
				
				dateArr = ac.getDateArr();
	    		totalInfectedCaseArr = ac.getTotalInfectedCaseArr();
	    		totalRecoveryCaseArr = ac.getTotalRecoveryCase();
	    		totalDeathCaseArr = ac.getTotalDeathCase();
	    		
	    		newlyDiscoveredArr = ac.getNewlyDiscoveredCase();
	    		newlyRecoveredArr = ac.getNewlyRecoveredCase();
	    		newlyDeathArr = ac.getNewlyDeathCase();
			} catch(NumberFormatException err) {
        		AlertBox.display("Invalid input");
        	}
        });
        
        Button viewGraphButton = new Button("View graph");
        viewGraphButton.getStyleClass().setAll("btn", "btn-info");
        viewGraphButton.setOnAction(e -> {
        	try {
        		//showing the graph of the result in the new window
        		ResultSimulation result = new ResultSimulation(dateArr, totalInfectedCaseArr, totalDeathCaseArr, totalRecoveryCaseArr, newlyDiscoveredArr, newlyRecoveredArr);
    			Stage resultPage = result.getGraphPage();
    			resultPage.show();
        	} catch(NullPointerException err) {
        		AlertBox.display("You haven't create the city yet");
        	}
        });
        
        Button viewCityButton = new Button("View city");
        viewCityButton.getStyleClass().setAll("btn", "btn-success");
        viewCityButton.setOnAction(e -> {
        	try {
        		city.showCity().show();
        	} catch(NullPointerException err) {
        		AlertBox.display("You haven't create the city yet");
        	}
        });
        
        Button dashboardButton = new Button("Dashboard");
        dashboardButton.getStyleClass().setAll("btn", "btn-warning");
        dashboardButton.setOnAction(e -> {
        	try {
        		Stage dashboard = ActivitySimulator.getDashboard(dateArr, newlyDiscoveredArr, newlyRecoveredArr, newlyDeathArr);
        		dashboard.show();
        	} catch (NullPointerException err) {
        		AlertBox.display("You haven't create the city yet");
        	}
        });
        
        HBox hbox5 = new HBox();
        hbox5.getChildren().addAll(viewCityButton, viewGraphButton, dashboardButton);
        hbox5.setSpacing(20);
        
        content.add(hbox1, 0, 0);
        content.add(hbox2, 0,  1);
//        content.add(createCityButton, 0, 3);
//        content.add(viewGraphButton, 0,  4);
        content.add(hbox3,  0,  2);
        content.add(hbox4, 0, 3);
        content.add(createCityButton, 0, 4);
        content.add(hbox5,  0,  5);
        content.setHgap(10);
        content.setVgap(10);
        content.setAlignment(Pos.CENTER);
        
        panel.setBody(content);
//        content.setTop(isMco);
        Scene scene = new Scene(panel, 500, 500);
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

        primaryStage.setTitle("El-Corona");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        return primaryStage;
	}
	
	
}
