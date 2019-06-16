package encryption;

import java.io.Serializable;
import java.util.Objects;

public class Passwort implements Serializable {
	
	String bezeichnung;
	String passwort;
	
	public Passwort(String s1, String s2) {
		this.bezeichnung=s1;
		this.passwort=s2;
	}

}
