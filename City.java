
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.kordamp.bootstrapfx.scene.layout.Panel;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class City {
	private String[] buildingTypes = {"Residential Building", "Railway Station", "Factory", "Office Building", "Store", 
			"School", "Library", "Food Court", "Police Station", "Pharmacy", "Hospital", "Park", "Shopping Mall", "Post Office"};
	
	
	//basic attributes of a city
	private String cityName;
	private int numberOfPopulation;
	private int numberOfBuildings;
	
	//data structures to store data of city
	private Map<String, Integer> buildingsMapToNumber;
	private Graph<Person, String> socialNetworkOfHuman;
	private List<Building> buildingArr;
	private List<Person> humanArr;
	private List<Building> residentialBuildingArr;
	private List<Building> railwayStationArr;
	private List<Building> factoryArr;
	private List<Building> officeBuildingArr;
	private List<Building> storeArr;
	private List<Building> schoolArr;
	private List<Building> libraryArr;
	private List<Building> foodCourtArr;
	private List<Building> policeStationArr;
	private List<Building> pharmacyArr;
	private List<Building> hospitalArr;
	private List<Building> parkArr;
	private List<Building> shoppingMallArr;
	private List<Building> postOfficeArr;
	
	
	private List<Person> studentAndTeacherArr;
	private List<Person> factoryWorkerArr;
	private List<Person> officeWorkerArr;
	private List<Person> businessmanArr;
	private List<Person> joblessArr;
	private List<Person> policeArr;
	private List<Person> doctorArr;
	private List<Person> nurseArr;
	private List<Person> pharmacistArr;
	private List<Person> postmanArr;
	
	private Random rand = new Random();
	
	public City() {
		this.buildingArr = new ArrayList<>();
		this.humanArr = new ArrayList<>();
		this.buildingsMapToNumber = new HashMap<>();
		this.socialNetworkOfHuman = new Graph<>();
		this.residentialBuildingArr = new ArrayList<>();
		this.railwayStationArr = new ArrayList<>();
		this.factoryArr = new ArrayList<>();
		this.officeBuildingArr = new ArrayList<>();
		this.storeArr = new ArrayList<>();
		this.schoolArr = new ArrayList<>();
		this.libraryArr = new ArrayList<>();
		this.foodCourtArr = new ArrayList<>();
		this.policeStationArr = new ArrayList<>();
		this.pharmacyArr = new ArrayList<>();
		this.hospitalArr = new ArrayList<>();
		this.parkArr = new ArrayList<>();
		this.shoppingMallArr = new ArrayList<>();
		this.postOfficeArr = new ArrayList<>();
		this.studentAndTeacherArr = new ArrayList<>();
		this.factoryWorkerArr = new ArrayList<>();
		this.officeWorkerArr = new ArrayList<>();
		this.businessmanArr = new ArrayList<>();
		this.joblessArr = new ArrayList<>();
		this.policeArr = new ArrayList<>();
		this.doctorArr = new ArrayList<>();
		this.nurseArr = new ArrayList<>();
		this.pharmacistArr = new ArrayList<>();
		this.postmanArr = new ArrayList<>();
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public int getNumberOfPopulation() {
		return numberOfPopulation;
	}
	
	public void setNumberOfPopulation(int numberOfPopulation) {
		this.numberOfPopulation = numberOfPopulation;
	}
	
	public int getNumberOfBuildings() {
		return numberOfBuildings;
	}
	
	public void setNumberOfBuildings(int numberOfBuildings) {
		this.numberOfBuildings = numberOfBuildings;
	}
	
	public Map<String, Integer> getBuildingsMapToNumber() {
		return buildingsMapToNumber;
	}
	
	public void setBuildingsMapToNumber(HashMap<String, Integer> buildingsMapToNumber) {
		this.buildingsMapToNumber = buildingsMapToNumber;
	}
	
	public Graph<Person, String> getSocialNetworkOfHuman() {
		return socialNetworkOfHuman;
	}
	
	public void setSocialNetworkOfHuman(Graph<Person, String> socialNetworkOfHuman) {
		this.socialNetworkOfHuman = socialNetworkOfHuman;
	}
	
	public List<Building> getBuildingArr() {
		return buildingArr;
	}
	
	public void setBuildingArr(ArrayList<Building> buildingArr) {
		this.buildingArr = buildingArr;
	}
	
	public List<Person> getHumanArr() {
		return humanArr;
	}
	
	public void setHumanArr(ArrayList<Person> humanArr) {
		this.humanArr = humanArr;
	}
	
	public void generateCity(String name) {
	
		int numberOfPopulation = 5000;
		int numberOfRailwayStation = numberOfPopulation / 100;
		int numberOfSchool = numberOfPopulation * 20 / 100 / 100;
		int numberOfOfficeBuilding = numberOfPopulation * 60 / 100 * 30 / 100 / 50;
		int numberOfStore = numberOfPopulation * 60 / 100 * 15 / 100 / 10;
		int numberOfFactory = numberOfPopulation * 60 / 100 * 25 / 100 / 30;
		int numberOfResidentialBuilding = numberOfPopulation / 3;
		int numberOfLibrary = numberOfPopulation / 500;
		int numberOfFoodCourt = numberOfPopulation / 250;
		int numberOfPoliceStation = numberOfPopulation / 2500;
		int numberOfPharmacy = numberOfPopulation / 250;
		int numberOfHospital = numberOfPopulation / 400;
		int numberOfPark = numberOfPopulation / 400;
		int numberOfShoppingMall = numberOfPopulation / 500;
		int numberOfPostOffice = numberOfPopulation / 2500;
		int numberOfBuildings = numberOfRailwayStation + numberOfSchool +
								numberOfOfficeBuilding + numberOfStore + numberOfFactory + 
								numberOfResidentialBuilding + numberOfLibrary + 
								numberOfFoodCourt + numberOfPoliceStation + numberOfPharmacy +
								numberOfHospital + numberOfPark + numberOfShoppingMall + 
								numberOfPostOffice;
		
		
		buildingsMapToNumber.put("Railway Station", numberOfRailwayStation);
		buildingsMapToNumber.put("School", numberOfSchool);
		buildingsMapToNumber.put("Office Building", numberOfOfficeBuilding);
		buildingsMapToNumber.put("Store", numberOfStore);
		buildingsMapToNumber.put("Factory", numberOfFactory);
		buildingsMapToNumber.put("Residential Building", numberOfResidentialBuilding);
		buildingsMapToNumber.put("Library", numberOfLibrary);
		buildingsMapToNumber.put("Food Court", numberOfFoodCourt);
		buildingsMapToNumber.put("Police Station", numberOfPoliceStation);
		buildingsMapToNumber.put("Pharmacy", numberOfPharmacy);
		buildingsMapToNumber.put("Hospital", numberOfHospital);
		buildingsMapToNumber.put("Park", numberOfPark);
		buildingsMapToNumber.put("Shopping Mall", numberOfShoppingMall);
		buildingsMapToNumber.put("Post Office", numberOfPostOffice);
		
		this.setCityName(name);
		this.setNumberOfPopulation(numberOfPopulation);
		this.setNumberOfBuildings(numberOfBuildings);
		
		// generate building first
		for (int i = 0; i < numberOfBuildings; i++) {
			Building building;
			if (i < numberOfRailwayStation) {
				building = new Building("Railway Station", i);
				railwayStationArr.add(building);
			} else if (i < numberOfRailwayStation+numberOfSchool) {
				building = new Building("School", i);
				schoolArr.add(building);
			}
			else if (i < numberOfRailwayStation+numberOfSchool+numberOfOfficeBuilding) {
				building = new Building("Office Building", i);
				officeBuildingArr.add(building);
			}
			else if (i < numberOfRailwayStation+numberOfSchool+numberOfOfficeBuilding+numberOfStore) {
				building = new Building("Store", i);
				storeArr.add(building);
			}
			else if (i < numberOfRailwayStation+numberOfSchool+numberOfOfficeBuilding+numberOfStore+numberOfFactory){
				building = new Building("Factory", i);
				factoryArr.add(building);
			}
			else if (i < numberOfRailwayStation+numberOfSchool+numberOfOfficeBuilding+numberOfStore+numberOfFactory+numberOfResidentialBuilding){
				building = new Building("Residential Building", i);
				residentialBuildingArr.add(building);
			} else if (i < numberOfRailwayStation+numberOfSchool+numberOfOfficeBuilding+
					numberOfStore+numberOfFactory+numberOfResidentialBuilding+numberOfLibrary) {
				building = new Building("Library", i);
				libraryArr.add(building);
			} else if (i < numberOfRailwayStation+numberOfSchool+numberOfOfficeBuilding+
					numberOfStore+numberOfFactory+numberOfResidentialBuilding+numberOfLibrary+
					numberOfFoodCourt) {
				building = new Building("Food Court", i);
				foodCourtArr.add(building);
			} else if (i < numberOfRailwayStation+numberOfSchool+numberOfOfficeBuilding+
					numberOfStore+numberOfFactory+numberOfResidentialBuilding+numberOfLibrary+
					numberOfFoodCourt+numberOfPoliceStation) {
				building=  new Building("Police Station", i);
				policeStationArr.add(building);
			} else if (i < numberOfRailwayStation+numberOfSchool+numberOfOfficeBuilding+
					numberOfStore+numberOfFactory+numberOfResidentialBuilding+numberOfLibrary+
					numberOfFoodCourt+numberOfPoliceStation+numberOfPharmacy) {
				building = new Building("Pharmacy", i);
				pharmacyArr.add(building);
			} else if (i < numberOfRailwayStation+numberOfSchool+numberOfOfficeBuilding+
					numberOfStore+numberOfFactory+numberOfResidentialBuilding+numberOfLibrary+
					numberOfFoodCourt+numberOfPoliceStation+numberOfPharmacy+numberOfHospital) {
				building = new Building("Hospital", i);
				hospitalArr.add(building);
			} else if (i < numberOfRailwayStation+numberOfSchool+numberOfOfficeBuilding+
					numberOfStore+numberOfFactory+numberOfResidentialBuilding+numberOfLibrary+
					numberOfFoodCourt+numberOfPoliceStation+numberOfPharmacy+numberOfHospital+
					numberOfPark) {
				building = new Building("Park", i);
				parkArr.add(building);
			} else if (i < numberOfRailwayStation+numberOfSchool+numberOfOfficeBuilding+
					numberOfStore+numberOfFactory+numberOfResidentialBuilding+numberOfLibrary+
					numberOfFoodCourt+numberOfPoliceStation+numberOfPharmacy+numberOfHospital+
					numberOfPark+numberOfShoppingMall) {
				building = new Building("Shopping Mall", i);
				shoppingMallArr.add(building);
			} else {
				building = new Building("Post Office", i);
				postOfficeArr.add(building);
			}
			buildingArr.add(building);
		}
		
		// generate the population
		for (int i = 0; i < numberOfPopulation; i++) {
			Person person = new Person();
			person.generatePerson();
			person.setId(i);
			person.setStayPlace(residentialBuildingArr.get(rand.nextInt(residentialBuildingArr.size())));
			String occupation = person.getOccupation();
			if (occupation.equals("Student") || occupation.equals("Teacher")) {
				person.setWorkingPlace(schoolArr.get(rand.nextInt(schoolArr.size())));
				studentAndTeacherArr.add(person);
			}
			else if (occupation.equals("Factory Worker")) {
				person.setWorkingPlace(factoryArr.get(rand.nextInt(factoryArr.size())));
				factoryWorkerArr.add(person);
			}
			else if (occupation.equals("Office Worker")) {
				person.setWorkingPlace(officeBuildingArr.get(rand.nextInt(officeBuildingArr.size())));
				officeWorkerArr.add(person);
			}
			else if (occupation.equals("Businessman")) {
				if (Math.random() < 0.8) {
					person.setWorkingPlace(storeArr.get(rand.nextInt(storeArr.size())));
				} else {
					person.setWorkingPlace(shoppingMallArr.get(rand.nextInt(shoppingMallArr.size())));
				}
				businessmanArr.add(person);
			}
			else if (occupation.equals("Jobless")) {
				person.setWorkingPlace(null);
				joblessArr.add(person);
			} else if (occupation.equals("Police")) {
				person.setWorkingPlace(policeStationArr.get(rand.nextInt(policeStationArr.size())));
				policeArr.add(person);
			} else if (occupation.equals("Doctor")) {
				person.setWorkingPlace(hospitalArr.get(rand.nextInt(hospitalArr.size())));
				doctorArr.add(person);
			} else if (occupation.equals("Nurse")) {
				person.setWorkingPlace(hospitalArr.get(rand.nextInt(hospitalArr.size())));
				nurseArr.add(person);
			} else if (occupation.equals("Pharmacist")) {
				person.setWorkingPlace(pharmacyArr.get(rand.nextInt(pharmacyArr.size())));
				pharmacistArr.add(person);
			} else if (occupation.equals("Postman")) {
				person.setWorkingPlace(postOfficeArr.get(rand.nextInt(postOfficeArr.size())));
				postmanArr.add(person);
			}
			humanArr.add(person);
			
			// store the person as a vertice into the social network graph
			socialNetworkOfHuman.addVertice(person);
		}
		
		// generate the family, put all the family in the same residential building, and also set the railway station 
		socialNetworkOfHuman = generateHumanRelationship(socialNetworkOfHuman, humanArr, residentialBuildingArr, railwayStationArr);
		
	}
	
	// this graph contains nodes of person whick links to another nodes with some relationship
	// this graph will generate the stayplace of the person
	// also generate the railaystation for the person
	
	private Graph<Person, String> generateHumanRelationship(Graph<Person, String> graph, List<Person> arr, List<Building> residentialBuildingArr, List<Building> railwayStationArr) {
		// this is to prevent the duplicate index of stayplace
		Set<Integer> temp = new HashSet<>();
		
		for (int i=0; i<arr.size(); i++) {
			Person person = arr.get(i);
			int numberOfFamily = rand.nextInt(9)+1;
			
			// set the stay place of the person
			// later all the family members will stay at the same place
			Building stayPlace;
			int c = 0;
			while (true) {
				int stayPlaceIndex = rand.nextInt(residentialBuildingArr.size());
				if (!temp.contains(stayPlaceIndex)) {
					stayPlace = residentialBuildingArr.get(stayPlaceIndex);
					person.setStayPlace(stayPlace);
					break;
				}				
				c++;
				if (c > 10000) {
					stayPlace = residentialBuildingArr.get(stayPlaceIndex);
					person.setStayPlace(stayPlace);
				}
			}
			// this is the railway station that the person will use to go to work
			Building railwayStation;
			int railwayStationIndex = rand.nextInt(railwayStationArr.size());
			railwayStation = railwayStationArr.get(railwayStationIndex);
			person.setRailwayStation(railwayStation);
			
			// generate family
			if (graph.getAdjacentList(person).size() == 0) {
				for (int j=0; j<numberOfFamily; j++) {
					int familyIndex = rand.nextInt(this.numberOfPopulation-i) + i;

					if (graph.getAdjacentList(arr.get(familyIndex)).size() == 0 && !arr.get(familyIndex).equals(person)) {

						graph.addEdge(person, arr.get(familyIndex), true, "family");
						// also add into arrlist
						person.getListOfFamilies().add(arr.get(familyIndex));
						arr.get(familyIndex).getListOfFamilies().add(person);
						// also setting the stayplace
						arr.get(familyIndex).setStayPlace(stayPlace);
						// also setting the railwayStation
						arr.get(familyIndex).setRailwayStation(railwayStation);
						
						List<Pair<Person, String>> list = graph.getAdjacentList(person);

						for (int k=0; k<list.size(); k++) {
							for (int l=k+1; l<list.size(); l++) {
								if (!graph.hasEdge(list.get(k).getKey(), list.get(l).getKey())) {
									graph.addEdge(list.get(k).getKey(), list.get(l).getKey(), true, "family");
									if (!list.get(k).getKey().getListOfFamilies().contains(list.get(l).getKey())) {
										list.get(k).getKey().getListOfFamilies().add(list.get(l).getKey());
									}
									if (!list.get(l).getKey().getListOfFamilies().contains(list.get(k).getKey())) {
										list.get(l).getKey().getListOfFamilies().add(list.get(k).getKey());
									}
								}
							}
						}
					} 
				}				
			}					
		}
		// generate friends
		for (int i=0; i<arr.size(); i++) {
			Person person = arr.get(i);
			int numberOfFriends = rand.nextInt(10);
			for (int j=0; j<numberOfFriends; j++) {
				int friendIndex = rand.nextInt(this.numberOfPopulation);
				if (!arr.get(friendIndex).equals(person) && 
						!person.getListOfFamilies().contains(arr.get(friendIndex))
						&& !person.getListOfFamilies().contains(arr.get(friendIndex))) {
					graph.addEdge(person, arr.get(friendIndex), true, "friend");
					person.getListOfFriends().add(arr.get(friendIndex));
					arr.get(friendIndex).getListOfFriends().add(person);
				}
				
			}
		}
		
		// generate classmates and colleagues for teacher
		for (int i = 0; i < studentAndTeacherArr.size(); i++) {
			Person person = studentAndTeacherArr.get(i);
			for (int j = 0; j < studentAndTeacherArr.size(); j++) {
				Person anotherPerson = studentAndTeacherArr.get(j);
				if (!person.equals(anotherPerson) && 
						!person.getListOfClassmatesOrColleagues().contains(anotherPerson)
						&& person.getWorkingPlace().equals(anotherPerson.getWorkingPlace()) ) {
					graph.addEdge(person, anotherPerson, true, "classmate/colleague");
					person.getListOfClassmatesOrColleagues().add(anotherPerson);
					anotherPerson.getListOfClassmatesOrColleagues().add(person);
				}
			}
		}
		
		// generate colleagues for factory worker
		for (int i = 0; i < factoryWorkerArr.size(); i++) {
			Person person = factoryWorkerArr.get(i);
			for (int j = 0; j < factoryWorkerArr.size(); j++) {
				Person anotherPerson = factoryWorkerArr.get(j);
				if (!person.equals(anotherPerson) && 
						!person.getListOfClassmatesOrColleagues().contains(anotherPerson)
						&& person.getWorkingPlace().equals(anotherPerson.getWorkingPlace()) 
						&& person.getOccupation().equals(anotherPerson.getOccupation())) {
					graph.addEdge(person, anotherPerson, true, "classmate/colleague");
					person.getListOfClassmatesOrColleagues().add(anotherPerson);
					anotherPerson.getListOfClassmatesOrColleagues().add(person);
				}
			}
		}
		
		// generate colleagues for office worker
		for (int i = 0; i < officeWorkerArr.size(); i++) {
			Person person = officeWorkerArr.get(i);
			for (int j = 0; j < officeWorkerArr.size(); j++) {
				Person anotherPerson = officeWorkerArr.get(j);
				if (!person.equals(anotherPerson) && 
						!person.getListOfClassmatesOrColleagues().contains(anotherPerson)
						&& person.getWorkingPlace().equals(anotherPerson.getWorkingPlace()) 
						&& person.getOccupation().equals(anotherPerson.getOccupation())) {
					graph.addEdge(person, anotherPerson, true, "classmate/colleague");
					person.getListOfClassmatesOrColleagues().add(anotherPerson);
					anotherPerson.getListOfClassmatesOrColleagues().add(person);
				}
			}
		}
		
		// generate colleagues for police
		for (int i = 0; i < policeArr.size(); i++) {
			Person person = policeArr.get(i);
			for (int j = 0; j < policeArr.size(); j++) {
				Person anotherPerson = policeArr.get(j);
				if (!person.equals(anotherPerson) && 
						!person.getListOfClassmatesOrColleagues().contains(anotherPerson)
						&& person.getWorkingPlace().equals(anotherPerson.getWorkingPlace()) 
						&& person.getOccupation().equals(anotherPerson.getOccupation())) {
					graph.addEdge(person, anotherPerson, true, "classmate/colleague");
					person.getListOfClassmatesOrColleagues().add(anotherPerson);
					anotherPerson.getListOfClassmatesOrColleagues().add(person);
				}
			}
		}
		
		// generate colleagues for doctor
		for (int i = 0; i < doctorArr.size(); i++) {
			Person person = doctorArr.get(i);
			for (int j = 0; j < doctorArr.size(); j++) {
				Person anotherPerson = doctorArr.get(j);
				if (!person.equals(anotherPerson) && 
						!person.getListOfClassmatesOrColleagues().contains(anotherPerson)
						&& person.getWorkingPlace().equals(anotherPerson.getWorkingPlace()) 
						&& person.getOccupation().equals(anotherPerson.getOccupation())) {
					graph.addEdge(person, anotherPerson, true, "classmate/colleague");
					person.getListOfClassmatesOrColleagues().add(anotherPerson);
					anotherPerson.getListOfClassmatesOrColleagues().add(person);
				}
			}
		}
		
		// generate colleagues for nurse
		for (int i = 0; i < nurseArr.size(); i++) {
			Person person = nurseArr.get(i);
			for (int j = 0; j < nurseArr.size(); j++) {
				Person anotherPerson = nurseArr.get(j);
				if (!person.equals(anotherPerson) && 
						!person.getListOfClassmatesOrColleagues().contains(anotherPerson)
						&& person.getWorkingPlace().equals(anotherPerson.getWorkingPlace()) 
						&& person.getOccupation().equals(anotherPerson.getOccupation())) {
					graph.addEdge(person, anotherPerson, true, "classmate/colleague");
					person.getListOfClassmatesOrColleagues().add(anotherPerson);
					anotherPerson.getListOfClassmatesOrColleagues().add(person);
				}
			}
		}
		
		// generate colleagues for pharmacist
		for (int i = 0; i < pharmacistArr.size(); i++) {
			Person person = pharmacistArr.get(i);
			for (int j = 0; j < pharmacistArr.size(); j++) {
				Person anotherPerson = pharmacistArr.get(j);
				if (!person.equals(anotherPerson) && 
						!person.getListOfClassmatesOrColleagues().contains(anotherPerson)
						&& person.getWorkingPlace().equals(anotherPerson.getWorkingPlace()) 
						&& person.getOccupation().equals(anotherPerson.getOccupation())) {
					graph.addEdge(person, anotherPerson, true, "classmate/colleague");
					person.getListOfClassmatesOrColleagues().add(anotherPerson);
					anotherPerson.getListOfClassmatesOrColleagues().add(person);
				}
			}
		}
		
		// generate colleagues for postman
		for (int i = 0; i < postmanArr.size(); i++) {
			Person person = postmanArr.get(i);
			for (int j = 0; j < postmanArr.size(); j++) {
				Person anotherPerson = postmanArr.get(j);
				if (!person.equals(anotherPerson) && 
						!person.getListOfClassmatesOrColleagues().contains(anotherPerson)
						&& person.getWorkingPlace().equals(anotherPerson.getWorkingPlace()) 
						&& person.getOccupation().equals(anotherPerson.getOccupation())) {
					graph.addEdge(person, anotherPerson, true, "classmate/colleague");
					person.getListOfClassmatesOrColleagues().add(anotherPerson);
					anotherPerson.getListOfClassmatesOrColleagues().add(person);
				}
			}
		}
		
		
		return graph;
	}
	
	public Stage showCity() {
		Stage stage = new Stage();
		Panel panel = new Panel(this.cityName + " City");
		panel.getStyleClass().add("panel-primary");  
		Label cityInformation = new Label();
		String buildingNumber = "{";
		for (int i = 0; i < buildingTypes.length; i++) {
			if (i % 5 == 0 && i != 0) {
				buildingNumber += "\n" + buildingTypes[i] + ": " + buildingsMapToNumber.get(buildingTypes[i]) + ", ";
			} else {
				buildingNumber += buildingTypes[i] + ": " + buildingsMapToNumber.get(buildingTypes[i]) + ", ";
			}
		}buildingNumber += "}";
		cityInformation.setText("This city consists of " + this.numberOfPopulation + " residents\n"
				+ "The number of buildings in city is " + this.numberOfBuildings + "\n"
				 + "The buildings in the city are " + buildingNumber + "\n");
		cityInformation.setFont(new Font("Arial", 14));
		
		Text text = new Text("Search a person ID in the " + this.cityName + " City");
		text.setFont(new Font("Arial", 18));
		
		Text getPersonId = new Text("Enter a person ID: ");
		TextField personId = new TextField();
		
		Button viewBasicButton = new Button("View basic information");
		Button viewActivityLogButton = new Button("View activity log");
		Button viewRelationshipButton = new Button("View relationship");
		
		Button viewHumanPopulationTableButton = new Button("View population");
		
		viewBasicButton.getStyleClass().addAll("btn", "btn-info");
		viewActivityLogButton.getStyleClass().addAll("btn", "btn-success");
		viewRelationshipButton.getStyleClass().addAll("btn", "btn-primary");
		
		viewHumanPopulationTableButton.getStyleClass().addAll("btn", "btn-danger");
		viewHumanPopulationTableButton.setAlignment(Pos.BASELINE_CENTER);
        viewHumanPopulationTableButton.getStyleClass().setAll("btn","btn-danger");
        viewHumanPopulationTableButton.setPrefSize(200, 50);
		
		viewRelationshipButton.setOnAction(e -> {
			try {
				boolean found = false;
				Person person = null;
				int id = Integer.parseInt(personId.getText());
				for (int i = 0; i <= id; i++) {
					if (humanArr.get(i).getId() == id) {
						found = true;
						person = humanArr.get(i);
						break;
					}
				}
				if (!found) {
					AlertBox.display("The person you want to find do not exist in the city");
				} else {
					showRelationship(person.getListOfFamilies(), person.getListOfFriends(), person.getListOfClassmatesOrColleagues()).show();
				}
				
				
			} catch(NumberFormatException err) {
				AlertBox.display("Invalid input");
			}catch(IndexOutOfBoundsException err) {
				AlertBox.display("Invalid input");
			}
		});

		viewActivityLogButton.setOnAction(e -> {
			try {
				boolean found = false;
				Person person = null;
				int id = Integer.parseInt(personId.getText());
				for (int i = 0; i <= id; i++) {
					if (humanArr.get(i).getId() == id) {
						found = true;
						person = humanArr.get(i);
						break;
					}
				}
				if (!found) {
					AlertBox.display("The person you want to find do not exist in the city");
				} else {
					showList(person.getActivityLog()).show();
				}
				
				
			} catch(NumberFormatException err) {
				AlertBox.display("Invalid input");
			} catch(IndexOutOfBoundsException err) {
				AlertBox.display("Invalid input");
			}
		});
		viewBasicButton.setOnAction(e -> {
			try {
				Person person = null;
				boolean found = false;
				int id = Integer.parseInt(personId.getText());

				for (int i = 0; i <= id; i++) {
					if (humanArr.get(i).getId() == id) {
						person = humanArr.get(i);
						found = true;
						break;
					}
				}
				if (!found) {
					AlertBox.display("The person you want to find do not exist in the city");
				} else {
					AlertBox.display(person.showFullInformation());
				}	
			} catch(NumberFormatException err) {
				AlertBox.display("Invalid input");
			}catch(IndexOutOfBoundsException err) {
				AlertBox.display("Invalid input");
			}
		});
		
		viewHumanPopulationTableButton.setOnAction(e -> {
			showHumanPopulation().show();
		});
		
		
		HBox hbox1 = new HBox();
		hbox1.getChildren().addAll(getPersonId, personId);
		
		
		ScrollPane root = new ScrollPane();
		VBox vbox = new VBox();
		HBox hbox2 = new HBox();
		hbox2.getChildren().addAll(viewBasicButton, viewActivityLogButton, viewRelationshipButton, viewHumanPopulationTableButton);
		hbox2.setSpacing(10);
		vbox.getChildren().addAll(cityInformation, viewHumanPopulationTableButton, text, hbox1, hbox2);
		vbox.setSpacing(20);
		root.setContent(vbox);

		panel.setBody(root);
		Scene scene = new Scene(panel, 800, 600);
		scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
		stage.setScene(scene);
		
		return stage;
	}
	
	public Stage showList(List<Activity> activityLog) {
		Stage stage = new Stage();
		ScrollPane root = new ScrollPane();

		VBox vbox = new VBox();
		Text label = new Text();
		label.setText(activityLog.toString());
		Button closeBtn = new Button("Close");
        closeBtn.getStyleClass().setAll("btn","btn-danger");
        closeBtn.setOnAction(e -> stage.close());
		vbox.getChildren().addAll(label, closeBtn);
		root.setContent(vbox);
		root.setFitToHeight(true);
		root.setFitToWidth(true);
		root.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		root.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
		stage.setScene(scene);
		return stage;
	}
	
	public Stage showRelationship(List<Person> listOfFamilies, List<Person> listOfFriends, List<Person> listOfColleagues) {
		Stage stage = new Stage();
		ScrollPane root = new ScrollPane();
		
		VBox vbox = new VBox();
		Text label1 = new Text();
		String familyList = "[";
		if (listOfFamilies.size() > 5) {
			for (int i = 0; i < listOfFamilies.size(); i++) {
				if (i%5 == 0 && i!=0) {
					familyList += listOfFamilies.get(i) + "\n";
				} else {
					familyList += listOfFamilies.get(i) + ", ";  
				}
			}
			familyList += "]";
			
		} else {
			familyList = listOfFamilies.toString();
		}
		label1.setText("Family list: \n" + familyList);
		
		Text label2 = new Text();
		String friendList = "[";
		if (listOfFriends.size() > 5) {
			for (int i = 0; i < listOfFriends.size(); i++) {
				if (i%5 == 0 && i!=0) {
					friendList += listOfFriends.get(i) + "\n";
				} else {
					friendList += listOfFriends.get(i) + ", ";  
				}
			}
			friendList += "]";
			
		} else {
			friendList = listOfFriends.toString();
		}
		label2.setText("Friend list: \n" + friendList);
		
		Text label3 = new Text();
		String colleagueList = "[";
		if (listOfColleagues.size() > 5) {
			for (int i = 0; i < listOfColleagues.size(); i++) {
				if (i%5 == 0 && i!=0) {
					colleagueList += listOfColleagues.get(i) + "\n";
				} else {
					colleagueList += listOfColleagues.get(i) + ", ";  
				}
			}
			colleagueList += "]";
			
		} else {
			colleagueList = listOfColleagues.toString();
		}
		
		label3.setText("Colleague list: \n" + colleagueList);
		
		label1.setFont(new Font("Arial", 15));
		label2.setFont(new Font("Arial", 15));
		label3.setFont(new Font("Arial", 15));
		
		Button closeBtn = new Button("Close");
        closeBtn.getStyleClass().setAll("btn","btn-danger");
        closeBtn.setOnAction(e -> stage.close());
		vbox.getChildren().addAll(label1, label2, label3, closeBtn);
		root.setContent(vbox);
		root.setFitToHeight(true);
		root.setFitToWidth(true);
		root.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		root.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
		stage.setScene(scene);
		
		
		return stage;
	}
	
	public Stage showHumanPopulation() {
		Stage stage = new Stage();
		TableView<Person> table = new TableView<>();
		
		TableColumn<Person, Integer> idColumn = new TableColumn<>("Person ID");
		TableColumn<Person, Integer> ageColumn = new TableColumn<>("Age");
		TableColumn<Person, String> occupationColumn = new TableColumn<>("Occupation");
		TableColumn<Person, Double> forgetfullnessColumn = new TableColumn<>("Forgetfullness");
		TableColumn<Person, Integer> educationColumn = new TableColumn<>("Education Level");
		TableColumn<Person, Integer> civicAwarenessColumn = new TableColumn<>("Civic Awareness");
		TableColumn<Person, Double> immunityLevelColumn = new TableColumn<>("Immunity Level");
		TableColumn<Person, Boolean> infectedColumn = new TableColumn<>("Infected");
		TableColumn<Person, Integer> infectedPeriodColumn = new TableColumn<>("Infected Period");
		TableColumn<Person, Boolean> quarantinedColumn = new TableColumn<>("Quarantined");
		
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
		occupationColumn.setCellValueFactory(new PropertyValueFactory<>("occupation"));
		forgetfullnessColumn.setCellValueFactory(new PropertyValueFactory<>("forgetfullness"));
		educationColumn.setCellValueFactory(new PropertyValueFactory<>("educationLevel"));
		civicAwarenessColumn.setCellValueFactory(new PropertyValueFactory<>("civicAwareness"));
		immunityLevelColumn.setCellValueFactory(new PropertyValueFactory<>("immunityLevel"));
		infectedColumn.setCellValueFactory(new PropertyValueFactory<>("infected"));
		infectedPeriodColumn.setCellValueFactory(new PropertyValueFactory<>("infectedPeriod"));
		quarantinedColumn.setCellValueFactory(new PropertyValueFactory<>("quarantined"));

		table.setItems(FXCollections.observableArrayList(humanArr));
		table.getColumns().addAll(idColumn, ageColumn, occupationColumn,
				forgetfullnessColumn, educationColumn, civicAwarenessColumn, 
				immunityLevelColumn, infectedColumn, infectedPeriodColumn, quarantinedColumn);
		
		
		ScrollPane scroll = new ScrollPane();
		scroll.setContent(table);
		Scene scene= new Scene(scroll);
		stage.setScene(scene);
		
		
		return stage;
	}
}
