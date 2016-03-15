package glowny;
import java.util.ArrayList;

import generatoryKrawedzi.GeneratorKrawedziScaleFree;


public class GrafListowy extends Graf{
	private ArrayList<ArrayList<Integer>> listaPolaczen;
	
	public GrafListowy(TypSieci typSieci, int liczbaWezlow, int liczbaKrawedzi){
		super(typSieci, liczbaWezlow, liczbaKrawedzi);
	}
	
	@Override
	protected void utworzKrawedzie(){
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
	
	public int[] getTablicaStopniWierzcholkow(){
		int[] tablicaStopniWierzcholkow = new int[listaPolaczen.size()];
		for(int i=0; i<listaPolaczen.size(); i++){
			tablicaStopniWierzcholkow[i] = listaPolaczen.get(i).size();
		}
		return tablicaStopniWierzcholkow;
	}
	
	public ArrayList<Integer> getListaSasiadowOsobnika(int indexOsobnika){
		return listaPolaczen.get(indexOsobnika);
	}
	
	public boolean czyPolaczone(int wezel1, int wezel2){
		if( listaPolaczen.get(wezel1).contains(wezel2) )
			return true;
		return false;
	}
	
	//zmieniam!!, dodaje 1 zeby gralo, zeby mozna bylo zrobic np rzadki graf (oryginalnie jest bez tej +1)
	public int getStopienWierzcholka(int wezel){
		return listaPolaczen.get(wezel).size();
		//return listaPolaczen.get(wezel).size() + 1;
	}
	
	public void dodajKrawedz(int wezel1, int wezel2){
		listaPolaczen.get(wezel1).add(wezel2);
		listaPolaczen.get(wezel2).add(wezel1);
	}
	
	protected int usunKrawedz(int wezel1, int wezel2){
		if( listaPolaczen.get(wezel1).contains(wezel2) ){
			listaPolaczen.get(wezel1).remove(wezel2);
			listaPolaczen.get(wezel2).remove(wezel1);
			return 0;
		}
		return -1;
	}


}