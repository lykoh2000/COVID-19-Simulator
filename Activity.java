import java.util.List;

public class Activity {
	private String date;
	private Building place;
	private List<Person> personMet;
	
	public Activity(String date, Building place, List<Person> personMet) {
		this.date = date;
		this.place = place;
		this.personMet = personMet;
	}	

	public Building getPlace() {
		return place;
	}
	
	public String getDate() {
		return date;
	}
	

	public void setDate(String date) {
		this.date = date;
	}
	

	public void setPlace(Building place) {
		this.place = place;
	}
	
	public List<Person> getPersonMet() {
		return personMet;
	}
	
	public void setPersonMet(List<Person> personMet) {
		this.personMet = personMet;
	}

	@Override
	public String toString() {
		return "Date=" + date + ", Place=" + place.toString() + ", Person Met=" + personMet.toString() + "]\n";
	}
}
