package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import glowny.ParametryRozkladu;
import glowny.StrategiaSzczepienia;
import glowny.TypSieci;

public class MyJFrame extends JFrame implements ActionListener {
	public MyJPanel myJPanel;
	
	public MyJFrame(){
		super("Hello World");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(730, 600);
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
		double gammaMyScaleFree = myJPanel.getGammaMyScaleFree();
		int liczbaOsobnikow = myJPanel.getLiczbaOsobnikow();
		int liczbaKrawedzi = myJPanel.getLiczbaKrawedzi();
		int poczatkowaLiczbaChorych = myJPanel.getPoczatkowaLiczbaChorych();
		int liczbaZaszczepionych = myJPanel.getLiczbaZaszczepionych();
		StrategiaSzczepienia strategiaSzczepienia = myJPanel.getStrategiaSzczepienia();
		double zakaznoscPatogenu = myJPanel.getZakaznoscPatogenu();
		ParametryRozkladu parametryRozkladuPodatnosciNaInfekcje = myJPanel.getParametryRozkladuPodatnosciNaInfekcje();
		int czasTrwaniaChorobyWDniach = myJPanel.getCzasTrwaniaChorobyWDniach();
		int liczbaDni = myJPanel.getLiczbaDni();
		int liczbaPowtorzenEpidemii = myJPanel.getLiczbaPowtorzenEpidemii();
		boolean wszystkieSymulacjeNaJednymGrafie = myJPanel.czyWszystkieSymulacjePrzeprowadzicNaJednymGrafie();
		
		Controller.start(typSieci,
				ppbPrzepieciaSmallWorld,
				gammaMyScaleFree,
				liczbaOsobnikow,
				liczbaKrawedzi,
				poczatkowaLiczbaChorych,
				liczbaZaszczepionych,
				strategiaSzczepienia,
				zakaznoscPatogenu,
				parametryRozkladuPodatnosciNaInfekcje,
				czasTrwaniaChorobyWDniach,
				liczbaDni,
				liczbaPowtorzenEpidemii,
				wszystkieSymulacjeNaJednymGrafie);
		
	}
	
	

}
