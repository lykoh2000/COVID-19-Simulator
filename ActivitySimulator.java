import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;


public class ActivitySimulator {
	private int c = 0;
	private List<Person> infectedList = new LinkedList<>();
	private List<Person> discoveredList = new LinkedList<>();
	private List<Person> recoveredList = new LinkedList<>();
	private List<Person> deathList = new LinkedList<>();
	
	private Random rand = new Random();
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	// some probability of the infected disease
	private final double INFECTIOUS_PROBABILITY_DIRECT_CONTACT = 0.9;
	private final double INFECTIOUS_PROBABILITY_INDIRECT_CONTACT = 0.005;
	private final double PRIMARY_DEATH_PROBABILITY = 0.05;
	private final double PRIMARY_RECOVER_PROBABILITY = 0.05;
	private final double DISCOVER_CHANCE = 0.5;
	
	private String[] dateArr;
	private int[] totalInfectedCase;
	private int[] totalRecoveredCase;
	private int[] totalDeathCase;
	private int[] totalSusceptible;
	
	private int[] newlyDiscoveredCase;
	private int[] newlyRecoveredCase;
	private int[] newlyDeathCase;
	
	public void simulate(List<Person> humanArr, List<Building> buildingArr, int days, boolean withContactTracer, boolean showContactTracer, boolean mco) {
		
		
		dateArr = new String[days];
		totalInfectedCase = new int[days];
		totalRecoveredCase = new int[days];
		totalDeathCase = new int[days];
		totalSusceptible = new int[days];
		
		newlyDiscoveredCase = new int[days];
		newlyRecoveredCase = new int[days];
		newlyDeathCase = new int[days];

		
		// the time will start at 8am 4 June 2020
		LocalDateTime localDateTime = LocalDateTime.of(2020, Month.JUNE, 4, 8, 0, 0);
		String formattedDateTime = localDateTime.format(formatter);
		//initialize 10 random people as covid-19 carrier
		for (int i = 0; i < 5; i++) {
			Person person = humanArr.get(rand.nextInt(humanArr.size()));
			person.setInfected(true);
			infectedList.add(person);
			person.setInfectedDate(localDateTime);
		}
		
		//setting some primary attribute
		boolean mcoTriggered = false;
		
		int dayCounter = 0;
		while (dayCounter < days) {
			int discoverCase = 0;
			int recoverCase = 0;
			int deathCase = 0;
			
			formattedDateTime = localDateTime.format(formatter);
			String date = formattedDateTime.split(" ")[0];
			String time = formattedDateTime.split(" ")[1];
			
			for (Person person : humanArr) {
				if (person.getQuarantinedPeriod() > 14 && !person.isInfected()) {
					person.setQuarantined(false);
					continue;
				}
				person.setQuarantinedPeriod(person.getQuarantinedPeriod()+1);
			}
			// 0.05% do not follow MCO rules
			if (discoveredList.size() > humanArr.size() * 0.1 && mco) {
				if (!mcoTriggered) {
					mcoTriggered = true;
					for (int i = 0; i < humanArr.size(); i++) {
						Person person = humanArr.get(i);
						if (Math.random() > 0.005) {
							person.setQuarantined(true);
						}
					}
					AlertBox.display("Confirmed case: "+ discoveredList.size() + "\tMCO is triggered");
					System.out.println(infectedList.size());
				}
				
			}
			
			int hourCounter = 0;
			while (hourCounter < 24) {
				formattedDateTime = localDateTime.format(formatter);
				date = formattedDateTime.split(" ")[0];
				time = formattedDateTime.split(" ")[1];
				
				// initialize a building record to all the building
				for (Building building : buildingArr) {
					building.getBuildingRecords().put(formattedDateTime, new LinkedList<>());
				}
				
				
				for (Person person : humanArr) {
					if (person.isQuarantined()) {
						continue;
					}
					String occupation = person.getOccupation();
					// if in the 7am morning, students/teachers need to go railway station, 
					if (time.equals("07:00:00")) {
						if (occupation.equals("Student") || occupation.equals("Teacher") || occupation.equals("Police") || occupation.equals("Doctor") || occupation.equals("Nurse")){
							// student need to go to railway station
							Building railwayStation = person.getRailwayStation();
							// generate some classmate he met at the railway station
							railwayStation.getBuildingRecords().get(formattedDateTime).add(person);
						}
					}
					// if in the 8 am morning, students/teachers need to arrive at school, factory worker and office worker need to go to railway station
					if (time.equals("08:00:00")) {
						// students and teacher part
						if (occupation.equals("Student") || occupation.equals("Teacher") || occupation.equals("Police") || occupation.equals("Doctor") || occupation.equals("Nurse")) {
							Building workplace = person.getWorkingPlace();
							workplace.getBuildingRecords().get(formattedDateTime).add(person);

						}
						// factory worker and office worker part(go to railway station)
						if (occupation.equals("Factory Worker") || occupation.equals("Office Worker") || occupation.equals("Pharmacist") || occupation.equals("Postman")) {
							// workers need to go to railway station
							Building railwayStation = person.getRailwayStation();
							railwayStation.getBuildingRecords().get(formattedDateTime).add(person);
						}	
					}
					
					// if 9am morning, office worker and factory worker should arrive at their working place
					// the store owner should go to railway station
					
					else if (time.equals("09:00:00")) {
						if (occupation.equals("Factory Worker") || occupation.equals("Office Worker") || occupation.equals("Pharmacist") || occupation.equals("Postman")) {
							Building workPlace = person.getWorkingPlace();
							workPlace.getBuildingRecords().get(formattedDateTime).add(person);

						}	
						
						if (occupation.equals("Businessman")) {
							// businessman need to go to railway station
							Building railwayStation = person.getRailwayStation();
							railwayStation.getBuildingRecords().get(formattedDateTime).add(person);
						}
					}
					
					// if 10am morning, businessman should arrive at their store
					else if (time.equals("10:00:00")) {
						if (occupation.equals("Businessman")) {
							// businessman need to go to railway station
							Building store = person.getWorkingPlace();
							store.getBuildingRecords().get(formattedDateTime).add(person);
						}
						
					}
					
					// if 2pm , students and teacher finish school, go to railway station
					else if (time.equals("14:00:00")) {
						if (occupation.equals("Student") || occupation.equals("Teacher")) {
							// student need to go to railway station
							Building railwayStation = person.getRailwayStation();
							// generate some classmate he met at the railway station
							railwayStation.getBuildingRecords().get(formattedDateTime).add(person);

						}
						// random simulate the jobless person to go somewhere, got 30% probability to go out
						if (occupation.equals("Jobless")) {
							if (Math.random() <= 0.3) {
								// can go to anyplace 
								boolean visitHouse = false;
								if (Math.random() < 0.1) {
									visitHouse = true;
								}
								if (visitHouse == true) {
									Building building = buildingArr.get(rand.nextInt(buildingArr.size()));
									do {
										building = buildingArr.get(rand.nextInt(buildingArr.size()));
										building.getBuildingRecords().get(formattedDateTime).add(person);
									} while(!building.getTypeOfBuilding().equals("Residential Building"));
								}
							}
						}
					}
					// 3pm student teacher also go home
					else if (time.equals("15:00:00")) {
						if (occupation.equals("Student") || occupation.equals("Teacher")) {
							Building home = person.getStayPlace();
							home.getBuildingRecords().get(formattedDateTime).add(person);
						} 
						// random simulate the jobless person to go somewhere, got 30% probability to go out
						if (occupation.equals("Jobless")) {
							if (Math.random() <= 0.3) {
								boolean visitHouse = false;
								if (Math.random() < 0.1) {
									visitHouse = true;
								}
								if (visitHouse == true) {
									Building building = buildingArr.get(rand.nextInt(buildingArr.size()));
									do {
										building = buildingArr.get(rand.nextInt(buildingArr.size()));
										building.getBuildingRecords().get(formattedDateTime).add(person);
									} while(!building.getTypeOfBuilding().equals("Residential Building"));
								}
							}
						}
					}
					// 5pm worker finish work, go to railway station
					else if (time.equals("17:00:00")) {
						if (occupation.equals("Factory Worker") || occupation.equals("Office Worker") || occupation.equals("Police") || occupation.equals("Postman") || occupation.equals("Doctor") || occupation.equals("Nurse") || occupation.equals("Pharmacist")) {
							// workers need to go to railway station
							Building railwayStation = person.getRailwayStation();
							railwayStation.getBuildingRecords().get(formattedDateTime).add(person);
						}	
					}
					// 6pm anyone can go any place
					else if (time.equals("18:00:00") || time.equals("19:00:00") || time.equals("20:00:00") || time.equals("21:00:00")) {
						//10%chance to go out
						if (!occupation.equals("Businessman") && Math.random() <= 0.1) {
							Building building;
							do {
								building = buildingArr.get(rand.nextInt(buildingArr.size()));
							} while (building.getTypeOfBuilding().equals("Residential Building"));

							building.getBuildingRecords().get(formattedDateTime).add(person);
						}
							
					}
					
					// 10pm, everyone need to go home
					else if (time.equals("22:00:00")) {
						if (person.getActivityLog().size() == 0 || !person.getActivityLog().get(person.getActivityLog().size()-1).getPlace().equals(person.getStayPlace())) {
							Building home = person.getStayPlace();
							home.getBuildingRecords().get(formattedDateTime).add(person);
						}
					}
					else if (Integer.parseInt(time.split(":")[0]) > 22 || Integer.parseInt(time.split(":")[0]) < 7) {
						continue;
					}
					else {
						// random simulate the jobless person to go somewhere, got 20% probability to go out
						if (occupation.equals("Jobless")) {
							if (Math.random() <= 0.2) {
								// can go to anyplace , go other people house has relatively smaller probability
								boolean visitHouse = false;
								if (Math.random() < 0.05) {
									visitHouse = true;
								}
								if (visitHouse == true) {
									Building building = buildingArr.get(rand.nextInt(buildingArr.size()));
									do {
										building = buildingArr.get(rand.nextInt(buildingArr.size()));
										building.getBuildingRecords().get(formattedDateTime).add(person);
									} while(!building.getTypeOfBuilding().equals("Residential Building"));
								}
							}
						}
					}
				}
				// after the activity is generated ...
				
				// at here, i want to generate who are the people meet each other, also virus should spread at here
				// only the one that the person knows will be inserted into the activity log of the person
				// however, all the person at the same place will be recorded in the building records
				// virus should spread among the friends and families at the same place because the person is more likely to contact with them
				
				for (Building building : buildingArr) {
					double probability = 0.005;
					if (building.getTypeOfBuilding().equals("Store")) {
						probability = 0.5;
					}
					
					
					List<Person> listOfPersonAtTheBuilding = building.getBuildingRecords().get(formattedDateTime);

					Map<Person, List<Person>> closeContactHashmap = new HashMap<>();
					Map<Person, List<Person>> lessContactHashmap = new HashMap<>();
					// for each person in that building, add the activity log to the person
					// the activity log consists of the families and friends and classmates of that person
					for (int i = 0; i < listOfPersonAtTheBuilding.size(); i++) {
						List<Person> closeContact = new LinkedList<>();
						List<Person> lessContact = new LinkedList<>();
						Person person = listOfPersonAtTheBuilding.get(i);
						
						// get the close contact of the person in the building
						for (int j = 0; j < listOfPersonAtTheBuilding.size(); j++) {
							if (!listOfPersonAtTheBuilding.get(j).equals(person) && 
									Math.random() < probability) {
								closeContact.add(listOfPersonAtTheBuilding.get(j));
								List<Person> temp = new LinkedList<>();
								temp.add(person);
								if (closeContactHashmap.containsKey(person)) {
									temp.addAll(closeContactHashmap.get(person));
								}
								closeContactHashmap.put(listOfPersonAtTheBuilding.get(j), temp);
							} else {
								lessContact.add(listOfPersonAtTheBuilding.get(j));
							}
						}
						
						if (closeContactHashmap.containsKey(person)) {
							closeContact.addAll(closeContactHashmap.get(person));
						}
						closeContactHashmap.put(person, closeContact);
						lessContactHashmap.put(person, lessContact);
						Activity activity = new Activity(formattedDateTime, building, closeContact);
						person.getActivityLog().add(activity);
						
					}
					
					// setting infected condition, ignore the residential building because will infect family at the last

					List<Person> infectedListInTheBuilding = new LinkedList<>();
					for (Person person : listOfPersonAtTheBuilding) {
						if (person.isInfected() && !person.getStayPlace().equals(building)) {
							infectedListInTheBuilding.add(person);
						}
					}
					// in here, i set the person could infect others only when the infected period is greater than 5
					for (int i = 0; i < infectedListInTheBuilding.size(); i++) {
						Person person = infectedListInTheBuilding.get(i);
						if (person.getInfectedPeriod() >= 5) {
							infect(closeContactHashmap.get(person), INFECTIOUS_PROBABILITY_DIRECT_CONTACT, localDateTime);
							infect(lessContactHashmap.get(person), INFECTIOUS_PROBABILITY_INDIRECT_CONTACT, localDateTime);
						}
					}
				
				}
				
				// update the time
				hourCounter++;
				localDateTime = localDateTime.plusHours(1);
	
			}
			// after a day, infect the infected person family
			for (int i = 0; i < infectedList.size(); i++) {
				Person person = infectedList.get(i);
				if (person.getInfectedPeriod() >= 5) {
					infect(person.getListOfFamilies(), INFECTIOUS_PROBABILITY_DIRECT_CONTACT, localDateTime);
				}
			}
			
			// update the infection information
			for (int i = 0; i < infectedList.size(); i++) {
				Person person = infectedList.get(i);
				person.setInfectedPeriod(person.getInfectedPeriod()+1);
			}
			
			// set the infected person to be discovered
			// if discovered, triggered contact tracer
			for (int i = 0; i < infectedList.size(); i++) {
				Person person = infectedList.get(i);
				//condition for the infected person to be discovered
				// when discovered, quarantine immediately to prevent virus spread
				// here i set 30% chance for the virus to be discovered
				// 5 days because the virus symptoms usually show up after 5 days
				// we set the first 2 layer person inside contact tracer tree to be quarantined
				if (person.getInfectedPeriod() >= 10 && Math.random() < DISCOVER_CHANCE && !discoveredList.contains(person)) {
					person.setQuarantined(true);
					person.setQuarantinedPeriod(0);
					discoveredList.add(person);
					discoverCase+=1;
					if (withContactTracer) {
//						System.out.println("Contact tracer triggered.....");					
						ContactTracer ct = new ContactTracer();
						Tree<Person> completeTree = ct.traceComplete(person, localDateTime);
						Tree<Person> interviewTree = ct.traceInterview(person, localDateTime);
						
						// from the interview tree, check if got infected person
						// we see 2 layer only
						TreeNode<Person> root = interviewTree.getRoot();
						int discoverFromTree = 0;

						List<TreeNode<Person>> children = root.getChild();
						for (int k = 0; k < children.size(); k++) {
							Person p = children.get(k).getValue();
							if (!p.isQuarantined()) {
								p.setQuarantined(true);
								p.setQuarantinedPeriod(0);
							}
							if (p.isInfected() && !discoveredList.contains(p)) {

								discoveredList.add(p);
								discoverCase+=1;
								discoverFromTree++;
								ContactTracer ctInner = new ContactTracer();
								Tree<Person> interviewTreeInner = ctInner.traceInterview(p, localDateTime);
								TreeNode<Person> innerRoot = interviewTreeInner.getRoot();

								List<TreeNode<Person>> childrenInner = innerRoot.getChild();
								for (int m = 0; m < childrenInner.size(); m++) {
									Person pInner = childrenInner.get(m).getValue();
									if (!pInner.isQuarantined()) {
										pInner.setQuarantined(true);
										pInner.setQuarantinedPeriod(0);
									}
									if (pInner.isInfected() && !discoveredList.contains(pInner)) {
										pInner.setQuarantined(true);
										discoveredList.add(pInner);
										discoverCase+=1;
									}
								}
								
							} else {
								List<TreeNode<Person>> innerChildren = children.get(k).getChild();
								for (int n = 0; n < innerChildren.size(); n++) {
									Person pInner = innerChildren.get(n).getValue();
									if (!pInner.isQuarantined()) {
										pInner.setQuarantined(true);
										pInner.setQuarantinedPeriod(0);
									}
									if (p.isInfected() && !discoveredList.contains(p)) {
										discoveredList.add(p);
										discoverCase+=1;
										discoverFromTree++;
									}
									
								}
							}
							
						}
						if (showContactTracer) {
							Stage ctTree = ct.showContactTracer(completeTree, interviewTree);
							ctTree.showAndWait();
							AlertBox.display(discoverFromTree + " person is detected to be diagnosed with COVID-19 from the contact tracer");
							boolean wantContinue = wantContinue();
							if(!wantContinue) {
								showContactTracer = false;
							}
						
						}
					}
					
				}
			}
			
			// set the infected person to be dead because of COVID-19
			for (int i = 0; i < infectedList.size(); i++) {
				Person person = infectedList.get(i);
				// at here the immunity level will affect the death probability
				// the higher the immunity level, the lower the chance of death
				if (person.getInfectedPeriod() > 19) {
					double deathChance = (100-person.getImmunityLevel()) / 100 * PRIMARY_DEATH_PROBABILITY;
					if (Math.random() < deathChance) {
						// here the person is dead
						// add to the death list
						// so we need to remove his data out of every data structure
						// include humanArr of the city, all of the people who has relationship with him
						// remove the person from the friend list, family list and colleagues list
						// remove from the infected list
						deathList.add(person);
						deathCase+=1;
						humanArr.remove(person);
						List<Person> temp1 = person.getListOfClassmatesOrColleagues();
						List<Person> temp2 = person.getListOfFamilies();
						List<Person> temp3 = person.getListOfFriends();
						for (int j = 0; j < temp1.size();j++) {
							Person p = temp1.get(j);
							p.getListOfClassmatesOrColleagues().remove(person);
						}
						
						for (int j = 0; j < temp2.size();j++) {
							Person p = temp2.get(j);
							p.getListOfFamilies().remove(person);
						}
						
						for (int j = 0; j < temp3.size();j++) {
							Person p = temp3.get(j);
							p.getListOfFriends().remove(person);
						}
					}
				}
				
			}
			
			// set the infected person to have some chance to recovered
			// the higher the immunity level, the higher the chance to recover
			// when the person is recovered, he gains immunity against covid-19 so never get infected again
			// remove the person from the infected list
			// remove the quarantined status of the person
			
			
			for (int i = 0; i < infectedList.size(); i++) {
				Person person = infectedList.get(i);
				double recoveredChance = person.getImmunityLevel() * PRIMARY_RECOVER_PROBABILITY;
				if (Math.random() < recoveredChance && person.getInfectedPeriod() >= 21) {
					person.setRecovered(true);
					recoverCase+=1;
					person.setInfected(false);
					infectedList.remove(person);
					person.setQuarantined(false);
					recoveredList.add(person);
					System.out.println(person + " recovered");
				}
			}

			System.out.println("Number of infected person"+infectedList.size());
			totalInfectedCase[dayCounter] = infectedList.size();
			totalDeathCase[dayCounter] = deathList.size();
			totalRecoveredCase[dayCounter] = recoveredList.size();
			
			newlyDiscoveredCase[dayCounter] = discoverCase;
			newlyRecoveredCase[dayCounter] = recoverCase;
			newlyDeathCase[dayCounter] = deathCase;
			
//			System.out.println("Number of recovered case " + recoveredList.size());
//			System.out.println("Number of discovered case " + discoveredList.size());
//			System.out.println("Number of death case: " + deathList.size());
			
			dateArr[dayCounter] = date;
			
			// update the day
			dayCounter++;
		}
		Alert simulationComplete = getFinishSimulation();
		simulationComplete.show();
		
		
	}
	
	private void infect(List<Person> listOfPerson, double infectionProbability, LocalDateTime dateTime) {
		
		for (Person person : listOfPerson) {
			if (Math.random() < infectionProbability*((100-person.getImmunityLevel())/100.0) && !person.isInfected() && !person.isRecovered()) {
				person.setInfected(true);
				infectedList.add(person);	
				person.setInfectedDate(dateTime);
			}
		}

		
	}
	
	public String[] getDateArr() {
		return this.dateArr;
	}
	
	public int[] getTotalInfectedCaseArr() {
		return this.totalInfectedCase;
	}
	
	public int[] getTotalRecoveryCase() {
		return this.totalRecoveredCase;
	}
	
	public int[] getTotalDeathCase() {
		return this.totalDeathCase;
	}
	
	public int[] getNewlyDiscoveredCase() {
		return this.newlyDiscoveredCase;
	}
	
	public int[] getNewlyRecoveredCase() {
		return this.newlyRecoveredCase;
	}
	
	public int[] getNewlyDeathCase() {
		return this.newlyDeathCase;
	}
	
	
	private boolean wantContinue() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to continue showing the result of contact tracer?");
		ButtonType okButton = new ButtonType("YES", ButtonBar.ButtonData.YES);
		ButtonType noButton = new ButtonType("NO", ButtonBar.ButtonData.NO);

		alert.getButtonTypes().setAll(okButton, noButton);
	    Optional<ButtonType> choose = alert.showAndWait();
	    return choose.get() == okButton;
	}
	private Alert getFinishSimulation() {
//		Stage stage = new Stage();
		
		Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText("City has been created. Simulation complete.");
		return a;
	}
	
	public static Stage getDashboard(String[] date, int[] newlyDiscoveredCase, int[] newlyRecoveredCase, int[] newlyDeathCase) {
		//covert multiple array into 2d array
		String[][] infoArr = new String[date.length+1][4];
		infoArr[0][0] = "Date";
		infoArr[0][1] = "Newly Confirmed Case";
		infoArr[0][2] = "Newly Recovered Case";
		infoArr[0][3] = "Newly Death Case";
		
		for (int i = 0; i < infoArr.length-1; i++) {
			infoArr[i+1][0] = date[i];
			infoArr[i+1][1] = Integer.toString(newlyDiscoveredCase[i]);
			infoArr[i+1][2] = Integer.toString(newlyRecoveredCase[i]);
			infoArr[i+1][3] = Integer.toString(newlyDeathCase[i]);
		}
		
		Stage stage = new Stage();
		TableView<String[]> table = new TableView<>();
		ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(infoArr));
        data.remove(0);
        for (int i = 0; i < infoArr[0].length; i++) {
            TableColumn<String[], String> tc = new TableColumn<>(infoArr[0][i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(200);
            table.getColumns().add(tc);
        }
        table.setItems(data);
		ScrollPane pane = new ScrollPane();
		pane.setContent(table);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
        
		return stage;
	}
	
}
