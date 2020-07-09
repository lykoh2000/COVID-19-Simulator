import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.kordamp.bootstrapfx.scene.layout.Panel;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ContactTracer {
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private HashSet<Person> personCollector1 = new HashSet<>();
	private HashSet<Person> personCollector2 = new HashSet<>();
	
	public ContactTracer() {
		
	}
	
	public Tree<Person> traceComplete(Person target, LocalDateTime currentDate) {
		TreeNode<Person> root = new TreeNode<>(target);

		LocalDateTime dateOfInfection = target.getInfectedDate();
		personCollector1.add(target); 
		Tree<Person> tree = new Tree<>(traceComplete(root, currentDate,dateOfInfection, 0));
//		try {
//			PrintWriter p =new PrintWriter(new FileOutputStream("Test.txt"));
//			p.println(target);
//			p.println("Infected data: "+ target.getInfectedDate());
//			p.println(target.getActivityLog());
//			
//			p.close();
//		} catch(IOException e) {
//			System.out.println("ERROR FILE");
//		}
//		System.out.println("Out loop");
		return tree;
	}
	
	// a recursive method to trace back
	
	public TreeNode<Person> traceComplete(TreeNode<Person> target, LocalDateTime currentDate, LocalDateTime dateOfInfection, int i) {
		if (i == 5)  {
			return null;
		}
		List<Activity> activityLog = target.getValue().getActivityLog();
		

		// we need to check which person the target might have infected after the date of infection
		// here this is the ground truth value
		// later need to do the interviews for simulating reality situation
		
		List<Activity> activityAfterDateOfInfection = new LinkedList<>();
		Set<Person> personMetAfterDateOfInfection = new HashSet<>();
		
		// loop through the activity log of the person, get the person who contact with this person
		for (Iterator<Activity> iterator = activityLog.iterator(); iterator.hasNext();) {
			Activity activity = iterator.next();
			LocalDateTime datetime = LocalDateTime.parse(activity.getDate(), formatter);
			if (datetime.isAfter(dateOfInfection)) {
				activityAfterDateOfInfection.add(activity);
			}
			
		}
		// this contains all the person who has the highest probability infected by this person
		for (Activity activity : activityAfterDateOfInfection) {
			personMetAfterDateOfInfection.addAll(activity.getPersonMet());
		}
		
		for (Person person : personMetAfterDateOfInfection) {
			if (!personCollector1.contains(person)) {
				TreeNode<Person> node = new TreeNode<>(person);
				personCollector1.add(person);
				target.addChild(traceComplete(node, currentDate,dateOfInfection, i+1));
			}
		}
		// adding family into the tree
		for (Person person : target.getValue().getListOfFamilies()) {
			if (!personCollector1.contains(person)) {
				TreeNode<Person> node = new TreeNode<>(person);
				personCollector1.add(person);
				target.addChild(traceComplete(node, currentDate,dateOfInfection, i+1));
			}
		}
		return target;
	}
	
	public Tree<Person> traceInterview(Person target, LocalDateTime currentDate) {
		TreeNode<Person> root = new TreeNode<>(target);

		LocalDateTime dateOfInfection = target.getInfectedDate();
		personCollector2.add(target); 
		Tree<Person> tree = new Tree<>(traceInterview(root, currentDate, dateOfInfection, 0));
//		try {
//			PrintWriter p =new PrintWriter(new FileOutputStream("Test.txt"));
//			p.println(target);
//			p.println("Infected data: "+ target.getInfectedDate());
//			p.println(target.getActivityLog());
//			
//			p.close();
//		} catch(IOException e) {
//			System.out.println("ERROR FILE");
//		}


		return tree;
	}
	
	// a recursive method to trace back
	
	public TreeNode<Person> traceInterview(TreeNode<Person> target, LocalDateTime currentDate, LocalDateTime dateOfInfection, int i) {
		if (i == 5)  {
			return null;
		}
		List<Activity> activityLog = target.getValue().getActivityLog();
		float forgetfullness = target.getValue().getForgetfullness();
	
		
		// we need to check which person the target might have infected after the date of infection
		// here this is the ground truth value
		// later need to do the interviews for simulating reality situation
		
		List<Activity> activityAfterDateOfInfection = new LinkedList<>();
		Set<Person> personMetAfterDateOfInfection = new HashSet<>();
		
		// loop through the activity log of the person, get the person who contact with this person
		for (Iterator<Activity> iterator = activityLog.iterator(); iterator.hasNext();) {
			Activity activity = iterator.next();
			LocalDateTime datetime = LocalDateTime.parse(activity.getDate(), formatter);
			if (datetime.isAfter(dateOfInfection)) {
				activityAfterDateOfInfection.add(activity);
			}
			
		}
		// this contains all the person who has the highest probability infected by this person
		for (Activity activity : activityAfterDateOfInfection) {
			personMetAfterDateOfInfection.addAll(activity.getPersonMet());
		}
		
		for (Person person : personMetAfterDateOfInfection) {
			// setting an extra condition , when person has higher forgetfullness , the more likely the person will forget the person
			if (!personCollector2.contains(person) && Math.random() > forgetfullness/100.0) {
				TreeNode<Person> node = new TreeNode<>(person);
				personCollector2.add(person);
				target.addChild(traceInterview(node, currentDate,dateOfInfection, i+1));
			}
		}
		// add family into tree
		for (Person person : target.getValue().getListOfFamilies()) {
			if (!personCollector2.contains(person)) {
				TreeNode<Person> node = new TreeNode<>(person);
				personCollector2.add(person);
				target.addChild(traceInterview(node, currentDate,dateOfInfection, i+1));
			}
		}
		return target;
	}
	
	
	
	public Stage showContactTracer(Tree<Person> realTree, Tree<Person> interviewTree) {
		Stage stage = new Stage();
		stage.setTitle("Contact tracer");

		       
		SplitPane rootPane = new SplitPane();

		TreeNode<Person> rootNode1 = realTree.getRoot();
		
		double differences = compareTree(realTree, interviewTree);
		Panel panel = new Panel("Target person: " + rootNode1.getValue().getId() + 
				"\nThe difference between the ground truth value and the result of the interview is " + differences + "%");
		
		
		panel.getStyleClass().add("panel-primary");  
		
		// the ground truth contact tracer
		TreeItem<String> root1 = new TreeItem<String>(realTree.getRoot().getValue().toString() + " \t1.00");
//		Text text = new Text();
//		text.setText(rootNode1.getValue().getId() + " is infected. Below are the possible infected people.");
//		text.setFont(Font.font ("Verdana", 20));
		root1.setExpanded(true);
		for (int i = 0; i < rootNode1.getChild().size(); i++) {
			TreeNode<Person> childLevel1 = rootNode1.getChild().get(i);
			TreeItem<String> nodeLevel1 = new TreeItem<>(childLevel1.getValue().toString() + " \t0.5");
			nodeLevel1.setExpanded(true);
			root1.getChildren().add(nodeLevel1);
			for (int j = 0; j < childLevel1.getChild().size(); j++) {
				TreeNode<Person> childLevel2 = childLevel1.getChild().get(j);
				TreeItem<String> nodeLevel2 = new TreeItem<>(childLevel2.getValue() + " \t0.25");
				nodeLevel2.setExpanded(true);
				nodeLevel1.getChildren().add(nodeLevel2);
				for (int k = 0; k < childLevel2.getChild().size(); k++) {
					TreeNode<Person> childLevel3 = childLevel2.getChild().get(k);
					TreeItem<String> nodeLevel3 = new TreeItem<>(childLevel3.getValue() + " \t0.125");
					nodeLevel3.setExpanded(true);
					nodeLevel2.getChildren().add(nodeLevel3);
					for (int l = 0; l < childLevel3.getChild().size(); l++) {
						TreeNode<Person> childLevel4 = childLevel3.getChild().get(l);
						TreeItem<String> nodeLevel4 = new TreeItem<>(childLevel4.getValue() + " \t0.0625");
						nodeLevel3.getChildren().add(nodeLevel4);
					}
				}
			}
		}
		// the interview tree
		System.out.println(interviewTree.getSize());
		TreeNode<Person> rootNode2 = interviewTree.getRoot();
		TreeItem<String> root2 = new TreeItem<String>(interviewTree.getRoot().getValue().toString() + " \t1.00");
		root2.setExpanded(true);
		for (int i = 0; i < rootNode2.getChild().size(); i++) {
			TreeNode<Person> childLevel1 = rootNode2.getChild().get(i);
			TreeItem<String> nodeLevel1 = new TreeItem<>(childLevel1.getValue().toString() + " \t0.5");
			nodeLevel1.setExpanded(true);
			root2.getChildren().add(nodeLevel1);
			for (int j = 0; j < childLevel1.getChild().size(); j++) {
				TreeNode<Person> childLevel2 = childLevel1.getChild().get(j);
				TreeItem<String> nodeLevel2 = new TreeItem<>(childLevel2.getValue() + " \t0.25");
				nodeLevel2.setExpanded(true);
				nodeLevel1.getChildren().add(nodeLevel2);
				for (int k = 0; k < childLevel2.getChild().size(); k++) {
					TreeNode<Person> childLevel3 = childLevel2.getChild().get(k);
					TreeItem<String> nodeLevel3 = new TreeItem<>(childLevel3.getValue() + " \t0.125");
					nodeLevel3.setExpanded(true);
					nodeLevel2.getChildren().add(nodeLevel3);
					for (int l = 0; l < childLevel3.getChild().size(); l++) {
						TreeNode<Person> childLevel4 = childLevel3.getChild().get(l);
						TreeItem<String> nodeLevel4 = new TreeItem<>(childLevel4.getValue() + " \t0.0625");
						nodeLevel3.getChildren().add(nodeLevel4);
					}
				}
			}
		}
		Text title1 = new Text("Ground truth result");
		Text title2 = new Text("Interview result");
		title1.setFont(Font.font ("Verdana", 20));
		title2.setFont(Font.font ("Verdana", 20));
		
		
		VBox vbox1 = new VBox();
		VBox vbox2 = new VBox();
		TreeView<String> treeView1 = new TreeView<>(root1);
		vbox1.getChildren().addAll(title1, treeView1);
		TreeView<String> treeView2 = new TreeView<>(root2);
		vbox2.getChildren().addAll(title2, treeView2);
		
		rootPane.getItems().addAll(vbox1, vbox2);
		panel.setBody(rootPane);
		Scene scene = new Scene(panel);
		scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
		stage.setScene(scene);
		

		return stage;
	}
	
	private double compareTree(Tree<Person> tree1, Tree<Person> tree2) {
		System.out.println(tree1.getSize());
		System.out.println(tree2.getSize());
		return Math.abs(tree1.getSize() - tree2.getSize())*1.0 / Math.max(tree1.getSize(), tree2.getSize()) * 100.0;
	}
	
	
}
