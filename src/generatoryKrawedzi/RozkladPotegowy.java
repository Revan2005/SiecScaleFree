package generatoryKrawedzi;

import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class RozkladPotegowy {
	private double[] dystrybuanta;
	private double[] znormalizowanyRozklad;
	private double gamma;
	private int a;
	private int b;
	
	public RozkladPotegowy(double gamma, int a, int b){
		if( (a < 1) || (a > b) )
			JOptionPane.showMessageDialog(new JFrame(), "Błędne parametry konstruktora rozkładu potęgowego.");
		
		this.gamma = gamma;
		this.a = a;
		this.b = b;
		generujDystrybuante();
	}
	
	private void generujDystrybuante(){
		double suma = 0;
		for(int i = a; i <= b; i++){
			suma += Math.pow(i, -gamma);
		}
		znormalizowanyRozklad = new double[(b-a)+1];
		int j = 0;
		for(int i = a; i <= b; i++){
			znormalizowanyRozklad[j] = Math.pow(i, -gamma) / suma;
			j++;
		}
		dystrybuanta = new double[znormalizowanyRozklad.length];
		dystrybuanta[0] = znormalizowanyRozklad[0];
		for(int i=1; i<znormalizowanyRozklad.length; i++){
			dystrybuanta[i] = dystrybuanta[i-1] + znormalizowanyRozklad[i];
		}
		//System.out.println("Rozklad: " + Arrays.toString(znormalizowanyRozklad));
		//System.out.println("Dystrybuanta: " + Arrays.toString(dystrybuanta));
	}

	public int losuj(){
		int probkaZRozkladu = a + b;
		Random random = new Random();
		double losowa = random.nextDouble();
		for(int i=0; i<dystrybuanta.length; i++){
			if(losowa < dystrybuanta[i]){
				probkaZRozkladu = a + i;
				//System.out.println("probkaZRozkladu: " + probkaZRozkladu);
				return probkaZRozkladu;
			}
		}
		//System.out.println("probkaZRozkladu: " + probkaZRozkladu);
		return probkaZRozkladu;
	}
	
	public double getSrednia(){
		double srednia = 0;
		int j=0;
		for(int i = a; i <= b; i++){
			srednia += znormalizowanyRozklad[j] * j;
			j++;
		}
		return srednia;
	}
	
	public void printujZnormalizowanyRozklad(){
		//System.out.println("Rozklad: " + Arrays.toString(znormalizowanyRozklad));
		double suma = 0;
		double srednia = 0;
		int j=0;
		for(int i = a; i <= b; i++){
			suma += znormalizowanyRozklad[j];
			srednia += znormalizowanyRozklad[j] * j;
			j++;
		}
		System.out.println("Suma rozkładu: " + suma);
		System.out.println("Średnia rozkładu: " + srednia);
	}
}
