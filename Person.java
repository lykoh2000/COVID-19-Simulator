import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Person {
	//some basic attributes
	private int id;
	private String occupation;
	private int age;
	private float forgetfullness;
	private int educationLevel;
	private int civicAwareness;
	private int immunityLevel;
	
	private Building stayPlace;
	private Building workingPlace;
	private Building railwayStation;
	
	//some data structure to store data of that person
	private List<Activity> activityLog;
	private List<Person> listOfFriends;
	private List<Person> listOfFamilies;
	private List<Person> listOfClassmatesOrColleagues;
	
	//some person data about COVID-19
	private boolean isQuarantined = false;
	private boolean isRecovered = false;
	private boolean isInfected = false;	
	private int infectedPeriod = 0;
	private LocalDateTime infectedDate;
	private boolean isDead = false;
	private int quarantinedPeriod = 0;

	private Random rand = new Random();
	
	public Person() {
		this.activityLog = new LinkedList<>();
		this.listOfFamilies = new ArrayList<>();
		this.listOfFriends = new ArrayList<>();
		this.listOfClassmatesOrColleagues = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getForgetfullness() {
		return forgetfullness;
	}

	public void setForgetfullness(float forgetfullness) {
		this.forgetfullness = forgetfullness;
	}

	public int getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(int educationLevel) {
		this.educationLevel = educationLevel;
	}

	public int getCivicAwareness() {
		return civicAwareness;
	}

	public void setCivicAwareness(int civicAwareness) {
		this.civicAwareness = civicAwareness;
	}

	public int getImmunityLevel() {
		return immunityLevel;
	}

	public void setImmunityLevel(int immunityLevel) {
		this.immunityLevel = immunityLevel;
	}

	public Building getStayPlace() {
		return stayPlace;
	}

	public void setStayPlace(Building stayPlace) {
		this.stayPlace = stayPlace;
	}

	public Building getWorkingPlace() {
		return workingPlace;
	}

	public void setWorkingPlace(Building workingPlace) {
		this.workingPlace = workingPlace;
	}
	
	public Building getRailwayStation() {
		return railwayStation;
	}
	
	public void setRailwayStation(Building railwayStation) {
		this.railwayStation = railwayStation;
	}
	
	public List<Activity> getActivityLog() {
		return activityLog;
	}

	public void setActivityLog(List<Activity> activityLog) {
		this.activityLog = activityLog;
	}

	public List<Person> getListOfFriends() {
		return listOfFriends;
	}

	public void setListOfFriends(List<Person> listOfFriends) {
		this.listOfFriends = listOfFriends;
	}

	public List<Person> getListOfFamilies() {
		return listOfFamilies;
	}

	public void setListOfFamilies(List<Person> listOfFamilies) {
		this.listOfFamilies = listOfFamilies;
	}

	public List<Person> getListOfClassmatesOrColleagues() {
		return listOfClassmatesOrColleagues;
	}

	public void setListOfClassmatesOrColleagues(List<Person> listOfClassmatesOrColleagues) {
		this.listOfClassmatesOrColleagues = listOfClassmatesOrColleagues;
	}

	public boolean isQuarantined() {
		return isQuarantined;
	}

	public void setQuarantined(boolean isQuarantined) {
		this.isQuarantined = isQuarantined;
	}

	public boolean isRecovered() {
		return isRecovered;
	}

	public void setRecovered(boolean isRecovered) {
		this.isRecovered = isRecovered;
	}

	public boolean isInfected() {
		return isInfected;
	}

	public void setInfected(boolean isInfected) {
		this.isInfected = isInfected;
	}

	public int getInfectedPeriod() {
		return infectedPeriod;
	}

	public void setInfectedPeriod(int infectedPeriod) {
		this.infectedPeriod = infectedPeriod;
	}
	
	public int getQuarantinedPeriod() {
		return this.quarantinedPeriod;
	}
	
	public void setQuarantinedPeriod(int quarantinedPeriod) {
		this.quarantinedPeriod = quarantinedPeriod;
	}
	
	public LocalDateTime getInfectedDate() {
		return infectedDate;
	}

	public void setInfectedDate(LocalDateTime infectedDate) {
		this.infectedDate = infectedDate;
	}
	
	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public void generatePerson() {
		int ageCategory = generateAgeCategory();
		this.setAge(generateAge(ageCategory));
		this.setForgetfullness(generateForgetfullness(ageCategory));
		this.setEducationLevel(generateEducationLevel(this.age));
		this.setCivicAwareness(this.educationLevel);
		this.setImmunityLevel(generateImmunityLevel(ageCategory));
		this.setOccupation(generateOccupation(ageCategory));
		
		
		
	}
	
	private int generateAgeCategory() {
		int num = rand.nextInt(10)+1;
		// 20% children, 60% adult, 20% senior citizens
		if (num <= 2) { //0 is children, 1 is adult, 2 is senior citizens
			return 0;
		} else if (num <= 8) {
			return 1;
		} else {
			return 2;
		}
	}
	
	private String generateOccupation(int category) {
		if (category == 0) {return "Student";}
		else if (category == 1) {
			int num = rand.nextInt(100)+1;
			if (num <= 3) {return "Jobless";}
			else if (num <= 18) {return "Teacher";}
			else if (num <= 40) {return "Factory Worker";}
			else if (num <= 60) {return "Office Worker";}
			else if (num <= 75) {return "Businessman";}
			else if (num <= 80) {return "Police";}
			else if (num <= 85) {return "Doctor";}
			else if (num <= 90) {return "Nurse";}
			else if (num <= 95) {return "Pharmacist";}
			else {return "Postman";}
		} else {return "Jobless";}
	}
	
	private float generateForgetfullness(int category) {
		if (category == 0) {
			return rand.nextFloat()*30; // 0-30
		} else if (category == 1) {
			return rand.nextFloat()*45+5; // 5-50
		} else {
			return rand.nextFloat()*40+30; // 30-70
		}
	}
	
	private int generateAge(int category) {

		if (category == 0) {
			return rand.nextInt(18); // 0-17
		} else if (category == 1) {
			return rand.nextInt(47)+18; // 18-64
		} else {
			return rand.nextInt(47)+64;
		}
	}
	

	
	private int generateEducationLevel(int age) {
		if (age <= 6) {
			return 0;
		} else if (age <= 12) {
			return 1;
		} else if (age <= 18) {
			return 2;
		} else {
			int num = rand.nextInt(100)+1;
			if (num <= 2) {
				return 1;
			} else if (num <= 10) {
				return 2;
			} else {
				return 3;
			}
		}
	}
	
	private int generateImmunityLevel(int category) {
		// immunity level 0 - 100%
		if (category == 0) {
			return rand.nextInt(31);
		} else if (category == 1) {
			return rand.nextInt(71) + 30;
		} else {
			return rand.nextInt(31);
		}
	}
	
	public String toString() {
		return "Person ID: " + this.id;
	}
	public String showFullInformation() {
		return "Person ID: " + this.id + "\n"
				 + "Age: " + this.age + "\n" 
				 + "Occupation: " + this.occupation + "\n"
				 + "Forgetfullness: " + this.forgetfullness + "\n"
				 + "Education Level: " + this.educationLevel + "\n"
				 + "Civic Awareness: " + this.civicAwareness + "\n" 
				 + "Immunity Level: " + this.immunityLevel + "\n"
				 + "Infected: " + this.isInfected + "\n" 
				 + "Infected Period: " + this.infectedPeriod + "\n" 
				 + "Quarantined: " + this.isQuarantined + "\n";
	}
	
	
	
}
