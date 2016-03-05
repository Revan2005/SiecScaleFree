
public class Osobnik {
	private StanOsobnika stanZdrowia;
	private int liczbaDniOdZachorowania;
	
	public Osobnik(StanOsobnika stanZdrowia){
		this.stanZdrowia = stanZdrowia;
		liczbaDniOdZachorowania = 0;
	}

	public int getLiczbaDniOdZachorowania(){
		return liczbaDniOdZachorowania;
	}
	
	public void zwiekszLiczbeDniOdZachorowania(){
		liczbaDniOdZachorowania++;
	}
	
	public void zwiekszLiczbeDniOdZachorowania(int liczbaDni){
		liczbaDniOdZachorowania += liczbaDni;
	}
	
	public StanOsobnika getStan(){
		return stanZdrowia;
	}
	
	public void setStan(StanOsobnika stanZdrowia){
		this.stanZdrowia = stanZdrowia;
	}
}
