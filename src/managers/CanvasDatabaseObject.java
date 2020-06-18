package managers;


/**
 * Class to group a database Object
 */
public class CanvasDatabaseObject {
	private int id;
	private String name;
	private String date;
	
	public CanvasDatabaseObject(int id, String name, String date) {
		this.id = id;
		this.name = name;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDate() {
		return date;
	}
	
}
