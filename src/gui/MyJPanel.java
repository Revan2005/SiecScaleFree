package gui;

import java.awt.TextField;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import glowny.ParametryRozkladu;
import glowny.StrategiaSzczepienia;
import glowny.TypSieci;

public class MyJPanel extends JPanel{
	
	private JComboBox<String> typSieciComboBox;
	private JTextField ppbPrzepieciaTextField;
	private JComboBox<String> strategiaSzczepieniaComboBox;
	private JTextField liczbaOsobnikowTextField;
	private JTextField liczbaKrawedziTextField;
	private JTextField poczatkowaLiczbaChorychTextField;
	private JTextField liczbaZaszczepionychTextField;
	private JCheckBox wszystkieSymulacjeNaJednymGrafieCheckBox;
	private JTextField liczbaPowtorzenEpidemiiTextField;
	private JTextField liczbaDniTextField;
	private JTextField czasTrwaniaChorobyTextField;
	private JTextField zakaznoscPatogenuTextField;
	private JTextField sredniaPodatnoscNaInfekcjeTextField;
	private JTextField odchylenieStandardowePodatnosciNaInfekcjeTextField;
	
	public MyJPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//typ sieci
		JLabel typSieciLabel = new JLabel("Wybierz typ sieci");
		typSieciComboBox = new JComboBox<String>();
		typSieciComboBox.addItem("Graf losowy");
		typSieciComboBox.addItem("Siec small world");
		typSieciComboBox.addItem("Siec scale free");
		typSieciComboBox.addItem("Siec hybrydowa"); //hybrydowa = small world + scale free
		typSieciComboBox.setSelectedIndex(1); //ustawiam domyslnie na small world
		add(typSieciLabel);
		add(typSieciComboBox);
		
		JLabel ppbPrzepieciaLabel = new JLabel("Ppb przepiecia (dotyczy tylko sieci Small World!)");
		ppbPrzepieciaTextField = new JTextField("   0.3  ");
		add(ppbPrzepieciaLabel);
		add(ppbPrzepieciaTextField);
		
		JLabel liczbaOsobnikowLabel = new JLabel("Liczba osobników");
		liczbaOsobnikowTextField = new JTextField("  10000   ");
		add(liczbaOsobnikowLabel);
		add(liczbaOsobnikowTextField);
		
		JLabel liczbaKrawedziLabel = new JLabel("Liczba krawędzi");
		liczbaKrawedziTextField = new JTextField("   50000  ");
		add(liczbaKrawedziLabel);
		add(liczbaKrawedziTextField);
		
		JLabel poczatkowaLiczbaChorychLabel = new JLabel("poczatkowaLiczbaChorych");
		poczatkowaLiczbaChorychTextField = new JTextField("   10  ");
		add(poczatkowaLiczbaChorychLabel);
		add(poczatkowaLiczbaChorychTextField);
		
		JLabel liczbaZaszczepionychLabel = new JLabel("Liczba zaszczepionych");
		liczbaZaszczepionychTextField = new JTextField("  200   ");
		add(liczbaZaszczepionychLabel);
		add(liczbaZaszczepionychTextField);
		
		//strategia szczepienia
		JLabel strategiaSzczepieniaLabel = new JLabel("Wybierz strategię szczepienia");
		strategiaSzczepieniaComboBox = new JComboBox<String>();
		strategiaSzczepieniaComboBox.addItem("Losowo");
		strategiaSzczepieniaComboBox.addItem("Wierzcholki z najwyzszym stopniem");
		strategiaSzczepieniaComboBox.addItem("Losowi znajomi losowych ludzi");
		strategiaSzczepieniaComboBox.addItem("Znajomi losowych ludzi z najwyzszym stopniem");
		strategiaSzczepieniaComboBox.setSelectedIndex(0); //domyslne bedzie szczepienie losowe
		add(strategiaSzczepieniaLabel);
		add(strategiaSzczepieniaComboBox);
		
		JLabel zakaznoscPatogenuLabel = new JLabel("WspolczynnikZakaznosciPatogenu");
		zakaznoscPatogenuTextField = new JTextField("   0.05  ");
		add(zakaznoscPatogenuLabel);
		add(zakaznoscPatogenuTextField);
		
		JLabel sredniaPodatnoscNaInfekcjeLabel = new JLabel("srednia podatnosc na infekcje (rozklad normalny wartosci zmiennych z tego rozkladu przycinam do przedizalu[0,1])");
		sredniaPodatnoscNaInfekcjeTextField = new JTextField("   0.5  ");
		add(sredniaPodatnoscNaInfekcjeLabel);
		add(sredniaPodatnoscNaInfekcjeTextField);

		JLabel odchylenieStandardowePodatnosciNaInfekcjeLabel = new JLabel("odchylenie standardowe podatnosci na infekcje (2 param rozkladu normalnego, zmienne z rozkladu przycinam do przedzialu [0,1])");
		odchylenieStandardowePodatnosciNaInfekcjeTextField = new JTextField("   0.1  ");
		add(odchylenieStandardowePodatnosciNaInfekcjeLabel);
		add(odchylenieStandardowePodatnosciNaInfekcjeTextField);
		
		JLabel czasTrwaniaChorobyLabel = new JLabel("CzasTrwaniaChorobyWDniach");
		czasTrwaniaChorobyTextField = new JTextField("  7   ");
		add(czasTrwaniaChorobyLabel);
		add(czasTrwaniaChorobyTextField);
		
		JLabel liczbaDniLabel = new JLabel("LiczbaDniSymulacji");
		liczbaDniTextField = new JTextField("   100  ");
		add(liczbaDniLabel);
		add(liczbaDniTextField);
		
		JLabel liczbaPowtorzenEpidemiiLabel = new JLabel("liczbaPowtorzenpidemii");
		liczbaPowtorzenEpidemiiTextField = new JTextField("   1  ");
		add(liczbaPowtorzenEpidemiiLabel);
		add(liczbaPowtorzenEpidemiiTextField);
		
		wszystkieSymulacjeNaJednymGrafieCheckBox = new JCheckBox("Czy wszystkie symulacje wykonać na tym samym grafie?");
		wszystkieSymulacjeNaJednymGrafieCheckBox.setSelected(true); //domyslnie zaznaczone
		add(wszystkieSymulacjeNaJednymGrafieCheckBox);
		
		/*
		typSieci,
		ppbPrzepieciaSmallWorld,
		liczbaOsobnikow, ok
		liczbaKrawedzi, ok
		poczatkowaLiczbaChorych, ok 
		liczbaZaszczepionych, ok
		strategiaSzczepienia,
		prawdopodobienstwoZarazenia, ok
		czasTrwaniaChorobyWDniach, ok
		liczbaDni,					 ok
		liczbaPowtorzenEpidemii,	ok
		wyswietlacWykresy,			ok
		wyswietlacSzczegoly			ok
		*/
	}
	
	public TypSieci getTypSieci(){
		String typSieciString = typSieciComboBox.getSelectedItem().toString();
		switch(typSieciString){
			case "Graf losowy":
				return TypSieci.RANDOM;
			case "Siec small world":
				return TypSieci.SMALL_WORLD;
			case "Siec scale free":
				return TypSieci.SCALE_FREE;
			case "Siec hybrydowa":
				return TypSieci.HYBRID;
			default:
				return TypSieci.RANDOM;
		}	
	}
	
	public double getPpbPrzepieciaSmallWorld(){
		String ppbString = ppbPrzepieciaTextField.getText();
		ppbString = ppbString.trim();
		double ppb = Double.parseDouble(ppbString);
		return ppb;
	}
	
	public int getLiczbaOsobnikow(){
		String lO = liczbaOsobnikowTextField.getText();
		lO = lO.trim();
		int loInt = Integer.parseInt(lO);
		return loInt;
	}
	
	public int getLiczbaKrawedzi(){
		String lK = liczbaKrawedziTextField.getText();
		lK = lK.trim();
		int lkInt = Integer.parseInt(lK);
		return lkInt;
	}
	
	public int getPoczatkowaLiczbaChorych(){
		String plc = poczatkowaLiczbaChorychTextField.getText();
		plc = plc.trim();
		int plcInt = Integer.parseInt(plc);
		return plcInt;
	}
	
	public int getLiczbaZaszczepionych(){
		String lZ = liczbaZaszczepionychTextField.getText();
		lZ = lZ.trim();
		int lzInt = Integer.parseInt(lZ);
		return lzInt;
	}
	
	public StrategiaSzczepienia getStrategiaSzczepienia(){
		String strategiaSzczepieniaString = strategiaSzczepieniaComboBox.getSelectedItem().toString();
		switch(strategiaSzczepieniaString){
			case "Losowo":
				return StrategiaSzczepienia.LOSOWE;
			case "Wierzcholki z najwyzszym stopniem":
				return StrategiaSzczepienia.OSOBNIKI_Z_NAJWYZSZYM_STOPNIEM;
			case "Losowi znajomi losowych ludzi":
				return StrategiaSzczepienia.WSKAZ_LOSOWEGO_ZNAJOMEGO;
			case "Znajomi losowych ludzi z najwyzszym stopniem":
				return StrategiaSzczepienia.WSKAZ_ZNAJOMEGO_Z_NAJWYZSZYM_STOPNIEM;
			default:
				return StrategiaSzczepienia.LOSOWE;
		}
	}
	
	public double getZakaznoscPatogenu(){
		String zakaznoscPatogenu = zakaznoscPatogenuTextField.getText();
		zakaznoscPatogenu = zakaznoscPatogenu.trim();
		double zakaznoscPatogenuDouble = Double.parseDouble(zakaznoscPatogenu);
		return zakaznoscPatogenuDouble;
	}
	
	public ParametryRozkladu getParametryRozkladuPodatnosciNaInfekcje(){
		String sredniaString = sredniaPodatnoscNaInfekcjeTextField.getText();
		sredniaString = sredniaString.trim();
		double sredniaDouble = Double.parseDouble(sredniaString);
		String odchylenieString = odchylenieStandardowePodatnosciNaInfekcjeTextField.getText();
		odchylenieString = odchylenieString.trim();
		double odchylenieDouble = Double.parseDouble(odchylenieString);
		return new ParametryRozkladu(sredniaDouble, odchylenieDouble);
	}
	
	public int getCzasTrwaniaChorobyWDniach(){
		String czas = czasTrwaniaChorobyTextField.getText();
		czas = czas.trim();
		int czasInt = Integer.parseInt(czas);
		return czasInt;
	}
	
	public int getLiczbaDni(){
		String lD = liczbaDniTextField.getText();
		lD = lD.trim();
		int ldInt = Integer.parseInt(lD);
		return ldInt;	
	}
	
	public int getLiczbaPowtorzenEpidemii(){
		String lP = liczbaPowtorzenEpidemiiTextField.getText();
		lP = lP.trim();
		int lpInt = Integer.parseInt(lP);
		return lpInt;
	}
	
	public boolean czyWszystkieSymulacjePrzeprowadzicNaJednymGrafie(){
		if(wszystkieSymulacjeNaJednymGrafieCheckBox.isSelected())
			return true;
		return false;
	}

}
