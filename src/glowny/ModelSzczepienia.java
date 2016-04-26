package glowny;
import java.util.List;
import java.util.Random;

public abstract class ModelSzczepienia {
	
	public static void zaszczep(StrategiaSzczepienia strategiaSzczepienia, Graf populacja, int liczbaOsobnikow){
		switch(strategiaSzczepienia){
			case LOSOWE:
				zaszczepLosowo(populacja, liczbaOsobnikow);
				break;
			case OSOBNIKI_Z_NAJWYZSZYM_STOPNIEM:
				zaszczepOsobnikiZMaxStopniem(populacja, liczbaOsobnikow);
				break;
			case WSKAZ_LOSOWEGO_ZNAJOMEGO:
				zaszczepWskazanychLosowoZnajomych(populacja, liczbaOsobnikow);
				break;
			case WSKAZ_ZNAJOMEGO_Z_NAJWYZSZYM_STOPNIEM:
				zaszczepWskazanychZnajomychZNajwyzszymStopniem(populacja, liczbaOsobnikow);
				break;
			default:
				break;
		}
	}
	
	private static void zaszczepLosowo(Graf populacja, int liczbaOsobnikow){
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
	
	private static void zaszczepOsobnikiZMaxStopniem(Graf populacja, int liczbaOsobnikow){
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
	
	private static void zaszczepWskazanychLosowoZnajomych(Graf populacja, int liczbaOsobnikow){
		Random rand = new Random();
		int liczbaZaszczepionych = 0;
		while(liczbaZaszczepionych < liczbaOsobnikow){
			int indexOsobnikaDoKtoregoDzwonimy = rand.nextInt(populacja.getLiczbaWezlow());
			List<Integer> listaZnajomych = populacja.getListaSasiadowOsobnika(indexOsobnikaDoKtoregoDzwonimy);
			if(listaZnajomych.size() < 1){
				continue;
			}
			int pozycjaWskazanegoZnajomegoNaLiscie = rand.nextInt(listaZnajomych.size());
			int indexWskazanegoOsobnika = listaZnajomych.get(pozycjaWskazanegoZnajomegoNaLiscie);
			if(populacja.getStanZdrowiaOsobnika(indexWskazanegoOsobnika).equals(StanOsobnika.ZDROWY)){
				populacja.setStanZdrowiaOsobnika(indexWskazanegoOsobnika, StanOsobnika.ODPORNY);
				liczbaZaszczepionych++;
			}
		}
	}
	
	private static void zaszczepWskazanychZnajomychZNajwyzszymStopniem(Graf populacja, int liczbaOsobnikow){
		//kiedy ten z najwyzszym stopniem jest juz zaszczepiony to szczepimy losowego!!!

		Random rand = new Random();
		int liczbaZaszczepionych = 0;
		while(liczbaZaszczepionych < liczbaOsobnikow){
			int indexOsobnikaDoKtoregoDzwonimy = rand.nextInt(populacja.getLiczbaWezlow());
			List<Integer> listaZnajomych = populacja.getListaSasiadowOsobnika(indexOsobnikaDoKtoregoDzwonimy);
			//System.out.println(listaZnajomych.size() + " " + liczbaZaszczepionych);
			if(listaZnajomych.size() < 1){
				continue;
			}
			int najwyzszyStopienZnajomegoZListy = 0;
			int indexWskazanegoOsobnika = 0;
			int indexZnajomegoWPopulacji;
			for(int i=0; i<listaZnajomych.size(); i++){
				indexZnajomegoWPopulacji = listaZnajomych.get(i);
				if(populacja.getStopienWierzcholka(indexZnajomegoWPopulacji) > najwyzszyStopienZnajomegoZListy){
					indexWskazanegoOsobnika = indexZnajomegoWPopulacji;
					najwyzszyStopienZnajomegoZListy = populacja.getStopienWierzcholka(indexZnajomegoWPopulacji);
				}
			}
			if(populacja.getStanZdrowiaOsobnika(indexWskazanegoOsobnika).equals(StanOsobnika.ZDROWY)){
				populacja.setStanZdrowiaOsobnika(indexWskazanegoOsobnika, StanOsobnika.ODPORNY);
				liczbaZaszczepionych++;
			} else { //jak ten z najwyzszym stopniem jest juz zaszczepiony to szczepimy pierwszeggo z brzegu (to tak jak losowo (moze nie do konca tak jak losowo ale nie zalezy nam tutaj na wlasciwej losowosci wystarczy ze poda ktoregokolwiek) a bedzie szybciej bo bez rand)
				int indexWPopulacji;
				for(int i=0; i<listaZnajomych.size(); i++){
					indexWPopulacji = listaZnajomych.get(i);
					if(populacja.getStanZdrowiaOsobnika(indexWPopulacji).equals(StanOsobnika.ZDROWY)){
						populacja.setStanZdrowiaOsobnika(indexWPopulacji, StanOsobnika.ODPORNY);
						liczbaZaszczepionych++;
						break;
					}
				}
			}
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
