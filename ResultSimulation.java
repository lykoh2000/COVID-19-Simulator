import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ResultSimulation extends Stage {
	Label label = new Label("Simulation Result");
	private String[] date;
	private int[] totalInfectedCase;
	private int[] totalDeathCase;
	private int[] totalRecoveredCase;
	private int[] newlyConfirmedCase;
	private int[] newlyRecoveredCase;
	
	
	public ResultSimulation(String[] date, int[] totalInfectedCase, int[] totalDeathCase, int[] totalRecoveredCase,
			int[] newlyConfirmedCase, int[] newlyRecoveredCase) {
		this.totalInfectedCase = totalInfectedCase;
		this.totalDeathCase = totalDeathCase;
		this.totalRecoveredCase = totalRecoveredCase;
		this.date = date;
		this.newlyConfirmedCase = newlyConfirmedCase;
		this.newlyRecoveredCase = newlyRecoveredCase;
	}
	
	public Stage getGraphPage() {
		Stage stage = new Stage();
		ScrollPane root = new ScrollPane();
		LineChart<String, Number> lineChart1 = createLineChart(date, totalInfectedCase, "Total Infected Case vs Time", "Date", "Total Infected Case", false);
		LineChart<String, Number> lineChart2 = createLineChart(date, totalRecoveredCase, "Total Recovered Case vs Time", "Date", "Total Recovered Case", true);
		LineChart<String, Number> lineChart3 = createLineChart(date, totalDeathCase, "Total Death Case vs Time", "Date", "Total Death Case", true);
		LineChart<String, Number> lineChart4 = createLineChart(date, newlyConfirmedCase, "New Confirmed Cases", "Date", "Number of cases", true);
		LineChart<String, Number> lineChart5 = createLineChart(date, newlyRecoveredCase, "Newly Recovered Cases", "Date", "Number of cases", true);
		
		FlowPane layer1 = new FlowPane();
		layer1.getChildren().addAll(lineChart1, lineChart2, lineChart3, lineChart4, lineChart5);
		root.setContent(layer1);
		Scene scene = new Scene(root, 600, 550);
		stage.setScene(scene);
		return stage;
	}
	
	public static Stage getLoadingPage() {
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		Text message = new Text();
		message.setText("Simulating the COVID-19 spread in the city. Please wait for a while");
		root.setCenter(message);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		return stage;
	}
	
	private LineChart<String, Number> createLineChart(String[] xAxisValues, int[] yAxisValues, String title, String xAxisName, String yAxisName, boolean autoranging) {
	    //defining the axes
	    final CategoryAxis xAxis = new CategoryAxis();
	    final NumberAxis yAxis = new NumberAxis();
	    xAxis.setLabel(xAxisName);
	    yAxis.setLabel(yAxisName);
	    if (!autoranging) {
	    	yAxis.setAutoRanging(false);
	    	yAxis.setLowerBound(0);
	    	yAxis.setUpperBound(5000);
	    	yAxis.setTickUnit(1000);
	    }
	    //creating the chart
	    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

	    lineChart.setTitle(title);
	    //defining a series
	    XYChart.Series<String, Number> series = new LineChart.Series<>();
//	    series.setName(xAxisName);
	    //populating the series with data
	    for (int i = 0; i < yAxisValues.length; i++) {
	        XYChart.Data<String, Number> data = new LineChart.Data<>(xAxisValues[i], yAxisValues[i]);
	        series.getData().add(data);
	    }
	    lineChart.getData().add(series);
	    lineChart.setLegendVisible(false);
	    return lineChart;
	}
	
}
