import java.util.ArrayList;
import java.util.Random;


public class Graf {
	private int liczbaWezlow;
	private ArrayList<Osobnik> listaOsobnikow;
	private ArrayList<ArrayList<Integer>> listaPolaczen;
	
	public Graf(int liczbaWezlow, int liczbaKrawedzi){
		this.liczbaWezlow = liczbaWezlow;
		listaPolaczen = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < liczbaWezlow; i++){
			listaPolaczen.add(new ArrayList<Integer>());
		}
		GeneratorKrawedzi generator = new GeneratorKrawedzi();
		generator.generujKrawedzie(this, liczbaKrawedzi);
		//Osobnik osobnik = new Osobnik(StanOsobnika.CHORY);
		inicjalizujListeOsobnikow();
	}
	
	private void inicjalizujListeOsobnikow(){
		listaOsobnikow = new ArrayList<Osobnik>();
	}
	
	public boolean czyPolaczone(int wezel1, int wezel2){
		if( listaPolaczen.get(wezel1).contains(wezel2) )
			return true;
		return false;
	}
	
	public StanOsobnika getStanZdrowiaOsobnika(int wezel){
		return listaOsobnikow.get(wezel).getStan();
	}
	
	public void setStanZdrowiaOsobnika(int wezel, StanOsobnika stanZdrowia){
		listaOsobnikow.get(wezel).setStan(stanZdrowia);
	}
	
	public int getStopienWierzcholka(int wezel){
		return listaPolaczen.get(wezel).size();
	}
	
	public int getLiczbaWezlow(){
		return liczbaWezlow;
	}
	
	public void dodajKrawedz(int wezel1, int wezel2){
		listaPolaczen.get(wezel1).add(wezel2);
		listaPolaczen.get(wezel2).add(wezel1);
	}
	
	private int usunKrawedz(int wezel1, int wezel2){
		if( listaPolaczen.get(wezel1).contains(wezel2) ){
			listaPolaczen.get(wezel1).remove(wezel2);
			listaPolaczen.get(wezel2).remove(wezel1);
			return 0;
		}
		return -1;
	}
	

}
