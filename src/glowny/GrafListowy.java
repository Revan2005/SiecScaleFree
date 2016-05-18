package glowny;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import generatoryKrawedzi.GeneratorKrawedziAutorskiScaleFree;
import generatoryKrawedzi.GeneratorKrawedziHybrid;
import generatoryKrawedzi.GeneratorKrawedziRandom;
import generatoryKrawedzi.GeneratorKrawedziScaleFree;
import generatoryKrawedzi.GeneratorKrawedziSmallWorld;


public class GrafListowy extends Graf{
	private ArrayList<ArrayList<Integer>> listaPolaczen;
	private int przesuniecie;
	
	public GrafListowy(TypSieci typSieci, int liczbaWezlow, int liczbaKrawedzi, ParametryRozkladu parametryRozkladuPodatnosciNaInfekcje){
		super(typSieci, liczbaWezlow, liczbaKrawedzi, parametryRozkladuPodatnosciNaInfekcje);
	}
	
	public GrafListowy(TypSieci typSieci, int liczbaWezlow, int liczbaKrawedzi, double dodatkowyParametr, ParametryRozkladu parametryRozkladuPodatnosciNaInfekcje){
		super(typSieci, liczbaWezlow, liczbaKrawedzi, dodatkowyParametr, parametryRozkladuPodatnosciNaInfekcje);
	}
	
	@Override
	public int getPrzesuniecieMyScaleFree(){
		if(typSieci != TypSieci.MY_SCALE_FREE){
			JOptionPane.showMessageDialog(new JFrame(), "Error");
		}
		return przesuniecie;
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
				GeneratorKrawedziScaleFree generatorScaleFree = new GeneratorKrawedziScaleFree();
				generatorScaleFree.generujKrawedzie(this, liczbaKrawedzi);
				break;
			case SMALL_WORLD:
				GeneratorKrawedziSmallWorld generatorSmallWorld = new GeneratorKrawedziSmallWorld(ppbPrzepieciaSmallWorld_Hybrid);
				generatorSmallWorld.generujKrawedzie(this, liczbaKrawedzi);
				break;
			case RANDOM:
				GeneratorKrawedziRandom generatorRandom = new GeneratorKrawedziRandom();
				generatorRandom.generujKrawedzie(this, liczbaKrawedzi);
				break;
			case HYBRID:
				GeneratorKrawedziHybrid generatorHybrid = new GeneratorKrawedziHybrid(ppbPrzepieciaSmallWorld_Hybrid);
				generatorHybrid.generujKrawedzie(this, liczbaKrawedzi);
				break;
			case MY_SCALE_FREE:
				GeneratorKrawedziAutorskiScaleFree generatorAutorskiScaleFree = new GeneratorKrawedziAutorskiScaleFree(gammaMyScaleFree);
				generatorAutorskiScaleFree.generujKrawedzie(this, liczbaKrawedzi);
				przesuniecie = generatorAutorskiScaleFree.getPrzesuniecie();
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
		if(!czyPolaczone(wezel1, wezel2)){
			listaPolaczen.get(wezel1).add(wezel2);
			listaPolaczen.get(wezel2).add(wezel1);
		}
	}
	
	public int usunKrawedz(int wezel1, int wezel2){
		if( listaPolaczen.get(wezel1).contains(wezel2) ){
			listaPolaczen.get(wezel1).remove(new Integer(wezel2));
			listaPolaczen.get(wezel2).remove(new Integer(wezel1));
			return 0;
		}
		return -1;
	}


}