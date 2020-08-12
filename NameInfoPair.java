package memberSystem;

public class NameInfoPair {
	//fields
	public Member value;
	public String key;
	
	// constructor
	public NameInfoPair(Member member) {
		this.key = this.value.getName();
		this.value = member;
	}
	
	// methods
	public String getKey() {
		return this.key;
	}
	
	public Member getValue() {
		return this.value;
	}
	
	public String setKey(String name) {
		String prevKey = this.key;
		this.key = name;
		this.value.setName(name);
		return prevKey;
	}
	
	public Member setValue(Member member) {
		Member prevMember = this.value;
		this.value = member;
		this.key = member.getName();
		return prevMember;
	}
	
	public boolean equals(NameInfoPair pair) {
		if (this.getKey() == pair.getKey() && this.getValue().equals(pair.getValue())) {
			return true;
		}
		return false;
	}

}
