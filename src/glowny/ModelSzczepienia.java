package glowny;
import java.util.Arrays;
import java.util.Random;

public abstract class ModelSzczepienia {
	
	public static void zaszczepLosowo(Graf populacja, int liczbaOsobnikow){
		Random rand = new Random();
		int liczbaZaszczepionych = 0;
		while(liczbaZaszczepionych < liczbaOsobnikow){
			int r = rand.nextInt(populacja.getLiczbaWezlow());
			if(populacja.getStanZdrowiaOsobnika(r).equals(StanOsobnika.ZDROWY)){
				populacja.setStanZdrowiaOsobnika(r, StanOsobnika.ODPORNY);
				liczbaZaszczepionych++;
			}
		}	
	}
	
	public static void zaszczepOsobnikiZMaxStopniem(Graf populacja, int liczbaOsobnikow){
		int liczbaZaszczepionych = 0;
		int[] stopnieWierzcholkow = populacja.getTablicaStopniWierzcholkow();
		while(liczbaZaszczepionych < liczbaOsobnikow){
			int indexOsobnikaZMaxKontaktami = getIndexOfMaxValue(stopnieWierzcholkow);
			if(populacja.getStanZdrowiaOsobnika(indexOsobnikaZMaxKontaktami).equals(StanOsobnika.ZDROWY)){
				populacja.setStanZdrowiaOsobnika(indexOsobnikaZMaxKontaktami, StanOsobnika.ODPORNY);
				liczbaZaszczepionych++;
			}
			//tak czy inaczej nalezy ustawic pole w tablicy na -1 zeby w nastepnej iteracji nie wybrac zniw tego samego osobnika
			//unikam nieskonczonej petli, albo zaszczepiania tylko pierwszego osobnika
			stopnieWierzcholkow[indexOsobnikaZMaxKontaktami] = -1;
		}	
	}
	
	private static int getIndexOfMaxValue(int[] tablica){
		int maxVal = -1;
		int indexOfMaxVal = 0;
		for(int i=0; i<tablica.length; i++){
			if(tablica[i] > maxVal){
				maxVal = tablica[i];
				indexOfMaxVal = i;
			}
		}
		return indexOfMaxVal;
	}

}
