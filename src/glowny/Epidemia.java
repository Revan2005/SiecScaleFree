package glowny;
import java.util.ArrayList;
import java.util.Random;

public class Epidemia {
	Graf populacja;
	double zakaznoscPatogenu;
	int czasTrwaniaChorobyWDniach;
	int[] zdrowi;
	int[] chorzy;
	int[] odporni;
	
	public Epidemia(Graf populacja, int poczatkowaLiczbaChorych, double prawdopodobienstwoZarazenia, int czasTrwaniaChorobyWDniach){
		this.populacja = populacja;
		this.zakaznoscPatogenu = prawdopodobienstwoZarazenia;
		this.czasTrwaniaChorobyWDniach = czasTrwaniaChorobyWDniach;
		ustalPoczatkowaGrupeChorych(poczatkowaLiczbaChorych);
	}
	
	public int[] getLiczbyZdrowychKazdegoDnia(){
		return zdrowi;
	}
	
	public int[] getLiczbyChorychKazdegoDnia(){
		return chorzy;
	}
	
	public int[] getLiczbyOdpornychKazdegoDnia(){
		return odporni;
	}
	
	public int[] getZachorowalnoscKazdegoDnia(){
		int[] zachorowalnoscKazdegoDnia = new int[chorzy.length];
		zachorowalnoscKazdegoDnia[0] = chorzy[0];
		for(int i=1; i<chorzy.length; i++){
			zachorowalnoscKazdegoDnia[i] = zdrowi[i-1] - zdrowi[i];
		}
		return zachorowalnoscKazdegoDnia;
	}
	
	private void ustalPoczatkowaGrupeChorych(int poczatkowaLiczbaChorych){
		Random rand = new Random();
		int liczbaZakazonych = 0;
		while(liczbaZakazonych < poczatkowaLiczbaChorych){
			int r = rand.nextInt(populacja.getLiczbaWezlow());
			if(populacja.getStanZdrowiaOsobnika(r).equals(StanOsobnika.ZDROWY)){
				populacja.setStanZdrowiaOsobnika(r, StanOsobnika.CHORY);
				liczbaZakazonych++;
			}
		}	
	}
	
	public void start(int liczbaDni){
		zdrowi = new int[liczbaDni];
		chorzy = new int[liczbaDni];
		odporni = new int[liczbaDni];
		aktualizujTabliceSIR(0);
		for(int i = 1; i < liczbaDni; i++){
			krok();
			aktualizujTabliceSIR(i);
		}
	}
	
	private void aktualizujTabliceSIR(int dzien){
		zdrowi[dzien] = populacja.getLiczbaZdrowych();
		chorzy[dzien] = populacja.getLiczbaChorych();
		odporni[dzien] = populacja.getLiczbaOdpornych();
	}
	
	private void krok(){
		ArrayList<Integer> listaSasiadow;
		for(int indexOsobnika = 0; indexOsobnika < populacja.getLiczbaWezlow(); indexOsobnika++){
			Osobnik osobnik = populacja.getOsobnik(indexOsobnika);
			//zarazanie
			if(osobnik.getStan().equals(StanOsobnika.ZDROWY)){
				listaSasiadow = populacja.getListaSasiadowOsobnika(indexOsobnika);
				for(Integer sasiad : listaSasiadow){
					if(populacja.getStanZdrowiaOsobnika(sasiad).equals(StanOsobnika.CHORY)){
						zarazZPrawdopodobienstwem(osobnik);
					}
				}
			}
			//zdrowienie
			if(populacja.getStanZdrowiaOsobnika(indexOsobnika).equals(StanOsobnika.CHORY)){
				if(osobnik.getLiczbaDniOdZachorowania() > czasTrwaniaChorobyWDniach){
					osobnik.setStan(StanOsobnika.ODPORNY);
				}
				//dodanie dnia choroby tym ktorzy sa chorzy
				osobnik.zwiekszLiczbeDniOdZachorowania();
			}
		}	
	}
	
	private void zarazZPrawdopodobienstwem(Osobnik osobnik){
		Random rand = new Random();
		double prawdopodobienstwoZarazenia = zakaznoscPatogenu * osobnik.wspolczynnikPodatnosciNaInfekcje;
		if(rand.nextDouble() <= prawdopodobienstwoZarazenia){
			osobnik.setStan(StanOsobnika.CHORY);
		}
	}
	
}
