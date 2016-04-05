package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import glowny.StrategiaSzczepienia;
import glowny.TypSieci;

public class MyJFrame extends JFrame implements ActionListener {
	public MyJPanel myJPanel;
	
	public MyJFrame(){
		super("Hello World");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		setLayout(new BorderLayout());
		myJPanel = new MyJPanel();
		add(myJPanel, BorderLayout.CENTER);
		JButton startButton = new JButton("Start!");
		startButton.addActionListener(this);
		add(startButton, BorderLayout.SOUTH);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TypSieci typSieci = myJPanel.getTypSieci();
		double ppbPrzepieciaSmallWorld = myJPanel.getPpbPrzepieciaSmallWorld();
		int liczbaOsobnikow = myJPanel.getLiczbaOsobnikow();
		int liczbaKrawedzi = myJPanel.getLiczbaKrawedzi();
		int poczatkowaLiczbaChorych = myJPanel.getPoczatkowaLiczbaChorych();
		int liczbaZaszczepionych = myJPanel.getLiczbaZaszczepionych();
		StrategiaSzczepienia strategiaSzczepienia = myJPanel.getStrategiaSzczepienia();
		double prawdopodobienstwoZarazenia = myJPanel.getPrawdopodobienstwoZarazenia();
		int czasTrwaniaChorobyWDniach = myJPanel.getCzasTrwaniaChorobyWDniach();
		int liczbaDni = myJPanel.getLiczbaDni();
		int liczbaPowtorzenEpidemii = myJPanel.getLiczbaPowtorzenEpidemii();
		boolean wyswietlacWykresy = myJPanel.czyWyswietlacWykresy();
		boolean wyswietlacSzczegoly = myJPanel.czyWyswietlacSzczegoly();
		
		Controller.start(typSieci,
				ppbPrzepieciaSmallWorld,
				liczbaOsobnikow,
				liczbaKrawedzi,
				poczatkowaLiczbaChorych,
				liczbaZaszczepionych,
				strategiaSzczepienia,
				prawdopodobienstwoZarazenia,
				czasTrwaniaChorobyWDniach,
				liczbaDni,
				liczbaPowtorzenEpidemii,
				wyswietlacWykresy,
				wyswietlacSzczegoly);
		
	}
	
	

}
