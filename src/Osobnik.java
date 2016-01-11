
public class Osobnik {
	private StanOsobnika stanZdrowia;
	
	public Osobnik(StanOsobnika stanZdrowia){
		this.stanZdrowia = stanZdrowia;
	}

	public StanOsobnika getStan(){
		return stanZdrowia;
	}
	
	public void setStan(StanOsobnika stanZdrowia){
		this.stanZdrowia = stanZdrowia;
	}
}
