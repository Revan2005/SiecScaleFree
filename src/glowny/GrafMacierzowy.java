package glowny;


import java.util.ArrayList;

import generatoryKrawedzi.GeneratorKrawedziScaleFree;


public class GrafMacierzowy extends Graf{
	private int[][] macierzPolaczen;
	
	public GrafMacierzowy(TypSieci typSieci, int liczbaWezlow, int liczbaKrawedzi){
		super(typSieci, liczbaWezlow, liczbaKrawedzi);
	}

	
	protected void utworzKrawedzie(){
		macierzPolaczen = new int[liczbaWezlow][liczbaWezlow];
		zerujMacierzPolaczen();
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
	
	private void zerujMacierzPolaczen(){
		for(int i=0; i<liczbaWezlow; i++){
			for(int j=0; j<liczbaWezlow; j++){
				macierzPolaczen[i][j] = 0;
			}
		}
	}
	
	public int[] getTablicaStopniWierzcholkow(){
		int[] tablicaStopniWierzcholkow = new int[liczbaWezlow];
		for(int i=0; i<liczbaWezlow; i++){
			tablicaStopniWierzcholkow[i] = 0;
			for(int j=0; j<liczbaWezlow; j++){
				if(macierzPolaczen[i][j] == 1){
					tablicaStopniWierzcholkow[i] ++;
				}
			}
		}
		return tablicaStopniWierzcholkow;
	}
	
	public ArrayList<Integer> getListaSasiadowOsobnika(int indexOsobnika){
		ArrayList<Integer> listaSasiadow = new ArrayList<Integer>();
		for(int j=0; j<liczbaWezlow; j++){
			if(macierzPolaczen[indexOsobnika][j] == 1){
				listaSasiadow.add(new Integer(j));
			}
		}
		return listaSasiadow;
	}
	
	public boolean czyPolaczone(int wezel1, int wezel2){
		if( macierzPolaczen[wezel1][wezel2] == 1 )
			return true;
		return false;
	}
	
	public int getStopienWierzcholka(int wezel){
		int stopienWierzholka = 0;
		for(int j=0; j<liczbaWezlow; j++){
			if( macierzPolaczen[wezel][j] ==1 ){
				stopienWierzholka++;
			}
		}
		return stopienWierzholka;
	}
	
	public void dodajKrawedz(int wezel1, int wezel2){
		macierzPolaczen[wezel1][wezel2] = 1;
		macierzPolaczen[wezel2][wezel1] = 1;
	}
	
	protected int usunKrawedz(int wezel1, int wezel2){
		if(macierzPolaczen[wezel1][wezel2] == 1){
			macierzPolaczen[wezel1][wezel2] = 0;
			macierzPolaczen[wezel2][wezel1] = 0;
			return 0;
		}
		return -1;
	}
	

}