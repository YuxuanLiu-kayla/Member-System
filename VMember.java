package memberSystem;

public class VMember extends Member{
	
	// fields
	public String birthday;
	
	public VMember(String name, String phoneNum, String birthday) {
		super(name, phoneNum);
		this.birthday = birthday;
	}
	
	public String getBirthday() {
		return this.birthday;
	}
	
	public String setBirthday(String birthday) {
		String prevBirthday = this.birthday;
		this.birthday = birthday;
		return prevBirthday;
	}
	
	@Override
	public boolean equals(Member m) {
		VMember v = (VMember) m;
		if (this.getName() == v.getName() && this.getPhoneNum() == v.getPhoneNum()) {
			if (this.getPoints() == v.getPoints() && this.getConsTimes() == v.getConsTimes()) {
				if (this.getBirthday() == v.getBirthday()) {
					return true;
				}
			}
		}
		return false;
	}
}
