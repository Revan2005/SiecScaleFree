package glowny;
import java.util.ArrayList;

import generatoryKrawedzi.GeneratorKrawedziScaleFree;


public class Graf {
	private TypSieci typSieci;
	private int liczbaWezlow;
	private int liczbaKrawedzi;
	private ArrayList<Osobnik> listaOsobnikow;
	private ArrayList<ArrayList<Integer>> listaPolaczen;
	
	public Graf(TypSieci typSieci, int liczbaWezlow, int liczbaKrawedzi){
		this.typSieci = typSieci;
		this.liczbaWezlow = liczbaWezlow;
		this.liczbaKrawedzi = liczbaKrawedzi;
		utworzKrawedzie();
		//Osobnik osobnik = new Osobnik(StanOsobnika.CHORY);
		inicjalizujListeOsobnikow();
	}
	
	private void utworzKrawedzie(){
		listaPolaczen = new ArrayList<ArrayList<Integer>>();
		//to jest lista list wiec inicjalizuje zewnetrzna liste pustymi listami
		for(int i = 0; i < liczbaWezlow; i++){
			listaPolaczen.add(new ArrayList<Integer>());
		}
		switch( typSieci ){
			case SCALE_FREE:
				GeneratorKrawedziScaleFree generator = new GeneratorKrawedziScaleFree();
				generator.generujKrawedzie(this, liczbaKrawedzi);
				break;
			case SMALL_WORLD:
				break;
			default:
				break;
		}
		
	}
	
	private void inicjalizujListeOsobnikow(){
		
		//===========================================================
		//tu tworze osobniki i tu moge ustalac ich odpornosc (pole podatnoscosobnikaNaInfekcje w klasie Osobnik)
		
		listaOsobnikow = new ArrayList<Osobnik>();
		for(int i=0; i<liczbaWezlow; i++){
			listaOsobnikow.add(new Osobnik(StanOsobnika.ZDROWY));
		}
	}
	
	public int[] getTablicaStopniWierzcholkow(){
		int[] tablicaStopniWierzcholkow = new int[listaPolaczen.size()];
		for(int i=0; i<listaPolaczen.size(); i++){
			tablicaStopniWierzcholkow[i] = listaPolaczen.get(i).size();
		}
		return tablicaStopniWierzcholkow;
	}
	
	public int getLiczbaChorych(){
		int liczbaChorych = 0;
		for(int i=0; i<liczbaWezlow; i++){
			if(getStanZdrowiaOsobnika(i).equals(StanOsobnika.CHORY))
				liczbaChorych++;
		}
		return liczbaChorych;
	}
	
	public int getLiczbaZdrowych(){
		int liczbaZdrowych = 0;
		for(int i=0; i<liczbaWezlow; i++){
			if(getStanZdrowiaOsobnika(i).equals(StanOsobnika.ZDROWY))
				liczbaZdrowych++;
		}
		return liczbaZdrowych;
	}
	
	public int getLiczbaOdpornych(){
		int liczbaOdpornych = 0;
		for(int i=0; i<liczbaWezlow; i++){
			if(getStanZdrowiaOsobnika(i).equals(StanOsobnika.ODPORNY))
				liczbaOdpornych++;
		}
		return liczbaOdpornych;
	}
	
	public Osobnik getOsobnik(int indexOsobnika){
		return listaOsobnikow.get(indexOsobnika);
	}
	
	public ArrayList<Integer> getListaSasiadowOsobnika(int indexOsobnika){
		return listaPolaczen.get(indexOsobnika);
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
