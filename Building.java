import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Building {
	private String typeOfBuilding;
	private int buildingID;
	private Map<String, List<Person>> buildingRecords;
	
	public Building(String typeOfBuilding, int buildingID) {
		this.typeOfBuilding = typeOfBuilding;
		this.buildingID = buildingID;	
		buildingRecords = new HashMap<>();
	}
	
	public String getTypeOfBuilding() {
		return typeOfBuilding;
	}
	public void setTypeOfBuilding(String typeOfBuilding) {
		this.typeOfBuilding = typeOfBuilding;
	}
	public int getBuildingID() {
		return buildingID;
	}
	public void setBuildingID(int buildingID) {
		this.buildingID = buildingID;
	}

	public Map<String, List<Person>> getBuildingRecords() {
		return buildingRecords;
	}

	public void setBuildingRecords(Map<String, List<Person>> buildingRecords) {
		this.buildingRecords = buildingRecords;
	}
	
	public String toString() {
		return typeOfBuilding + " ID: " + this.buildingID + " ";
	}
	
}
