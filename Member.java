package memberSystem;

public abstract class Member {
	// fields
	public String name;
	public String phoneNum;
	public int points;
	public int consTimes;
	
	// constructor
	public Member(String name, String phoneNum) {
		this.name = name;
		this.phoneNum = phoneNum;
		this.points = 30;
		this.consTimes = 1;
	}
	
	// methods
	public String getName() {
		return this.name;
	}
	
	public String getPhoneNum() {
		return this.phoneNum;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public int getConsTimes() {
		return this.consTimes;
	}
	
	public String setName(String name) {
		String prevName = this.name;
		this.name = name;
		return prevName;
	}
	
	public String setPhoneNum(String phoneNum) {
		String prevNum = this.phoneNum;
		this.phoneNum = phoneNum;
		return prevNum;
	}
	
	public int setPoints(int points) {
		int prevPoints = this.points;
		this.points = points;
		return prevPoints;
	}
	
	public int setConsTimes(int times) {
		int prevTimes = this.consTimes;
		this.consTimes = times;
		return prevTimes;
	}
	
	public boolean equals(Member m) {
		if (this.getName() == m.getName() && this.getPhoneNum() == m.getPhoneNum()) {
			if (this.getPoints() == m.getPoints() && this.getConsTimes() == m.getConsTimes()) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return "Customer" + this.getName() + "(" + this.getPhoneNum() + ")";
	}

}
