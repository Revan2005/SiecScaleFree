package glowny;
public class Osobnik {
	private StanOsobnika stanZdrowia;
	private int liczbaDniOdZachorowania;
	double wspolczynnikPodatnosciNaInfekcje; //przyjmuje wartosci od 0 do 1
	
	public Osobnik(StanOsobnika stanZdrowia){
		this.stanZdrowia = stanZdrowia;
		liczbaDniOdZachorowania = 0;
		this.wspolczynnikPodatnosciNaInfekcje = 1; 
	}
	
	public Osobnik(StanOsobnika stanZdrowia, double wspolczynnikPodatnosciNaInfekcje){
		this.stanZdrowia = stanZdrowia;
		liczbaDniOdZachorowania = 0;
		if(wspolczynnikPodatnosciNaInfekcje > 1)
			this.wspolczynnikPodatnosciNaInfekcje = 1.0;
		else if(wspolczynnikPodatnosciNaInfekcje < 0)
			this.wspolczynnikPodatnosciNaInfekcje = 0.0;
		else
			this.wspolczynnikPodatnosciNaInfekcje = wspolczynnikPodatnosciNaInfekcje;
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
