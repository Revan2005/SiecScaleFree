package glowny;
import java.util.ArrayList;

import generatoryKrawedzi.GeneratorKrawedziScaleFree;


public abstract class Graf {
	protected TypSieci typSieci;
	protected int liczbaWezlow;
	protected int liczbaKrawedzi;
	protected ArrayList<Osobnik> listaOsobnikow;
	protected double ppbPrzepieciaSmallWorld;
	
	public Graf(TypSieci typSieci, int liczbaWezlow, int liczbaKrawedzi){
		this.typSieci = typSieci;
		this.liczbaWezlow = liczbaWezlow;
		this.liczbaKrawedzi = liczbaKrawedzi;
		inicjalizujListeOsobnikow();
		utworzKrawedzie();
	}
	
	public Graf(TypSieci typSieci, int liczbaWezlow, int liczbaKrawedzi, double ppbPrzepieciaSmallWorld){
		this.typSieci = typSieci;
		this.liczbaWezlow = liczbaWezlow;
		this.liczbaKrawedzi = liczbaKrawedzi;
		this.ppbPrzepieciaSmallWorld = ppbPrzepieciaSmallWorld;
		inicjalizujListeOsobnikow();
		utworzKrawedzie();
	} 
	
	protected abstract void utworzKrawedzie();
	
	protected void inicjalizujListeOsobnikow(){
		//===========================================================
		//tu tworze osobniki i tu moge ustalac ich odpornosc (pole podatnoscosobnikaNaInfekcje w klasie Osobnik)
		listaOsobnikow = new ArrayList<Osobnik>();
		for(int i=0; i<liczbaWezlow; i++){
			listaOsobnikow.add(new Osobnik(StanOsobnika.ZDROWY));
		}
	}
	
	public abstract int[] getTablicaStopniWierzcholkow();
	
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
	
	public abstract ArrayList<Integer> getListaSasiadowOsobnika(int indexOsobnika);
	
	public abstract boolean czyPolaczone(int wezel1, int wezel2);
	
	public StanOsobnika getStanZdrowiaOsobnika(int wezel){
		return listaOsobnikow.get(wezel).getStan();
	}
	
	public void setStanZdrowiaOsobnika(int wezel, StanOsobnika stanZdrowia){
		listaOsobnikow.get(wezel).setStan(stanZdrowia);
	}
	
	public abstract int getStopienWierzcholka(int wezel);
	
	public int getLiczbaWezlow(){
		return liczbaWezlow;
	}
	
	public abstract void dodajKrawedz(int wezel1, int wezel2);
	
	public abstract int usunKrawedz(int wezel1, int wezel2);


}
