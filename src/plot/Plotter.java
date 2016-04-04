package plot;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Plotter extends ApplicationFrame{
	
	public Plotter( String applicationTitle, String chartTitle, int[] podatni, int[] chorzy, int[] odporni )
	{
	      super(applicationTitle);
	      JFreeChart xylineChart = ChartFactory.createXYLineChart(
	         chartTitle ,
	         "Dzień" ,
	         "Liczba osobników" ,
	         createDataset(podatni, chorzy, odporni) ,
	         PlotOrientation.VERTICAL ,
	         true , true , false);
	         
	      ChartPanel chartPanel = new ChartPanel( xylineChart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      final XYPlot plot = xylineChart.getXYPlot( );
	      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
	      renderer.setSeriesPaint( 0 , Color.YELLOW );
	      renderer.setSeriesPaint( 1 , Color.RED );
	      renderer.setSeriesPaint( 2 , Color.GREEN );
	      renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );
	      renderer.setSeriesStroke( 1 , new BasicStroke( 1.0f ) );
	      renderer.setSeriesStroke( 2 , new BasicStroke( 1.0f ) );
	      plot.setRenderer( renderer ); 
	      setContentPane( chartPanel ); 
	}
	
	public Plotter( String applicationTitle, String chartTitle, int[] podatni, int[] chorzy, int[] odporni, int[] noweZachorowaniaKazdegoDnia )
	{
	      super(applicationTitle);
	      JFreeChart xylineChart = ChartFactory.createXYLineChart(
	         chartTitle ,
	         "Dzień" ,
	         "Liczba osobników" ,
	         createDataset(podatni, chorzy, odporni, noweZachorowaniaKazdegoDnia) ,
	         PlotOrientation.VERTICAL ,
	         true , true , false);
	         
	      ChartPanel chartPanel = new ChartPanel( xylineChart );
	      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
	      final XYPlot plot = xylineChart.getXYPlot( );
	      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
	      renderer.setSeriesPaint( 0 , Color.YELLOW );
	      renderer.setSeriesPaint( 1 , Color.RED );
	      renderer.setSeriesPaint( 2 , Color.GREEN );
	      renderer.setSeriesPaint( 3 , Color.BLUE );
	      renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );
	      renderer.setSeriesStroke( 1 , new BasicStroke( 1.0f ) );
	      renderer.setSeriesStroke( 2 , new BasicStroke( 1.0f ) );
	      renderer.setSeriesStroke( 3 , new BasicStroke( 1.0f ) );
	      plot.setRenderer( renderer ); 
	      setContentPane( chartPanel ); 
	}

	
	public static void plot(int[] podatni, int[] chorzy, int[] odporni){
	      Plotter chart = new Plotter("Model SIR", "wykres w dniach", podatni, chorzy, odporni);
	      chart.pack( );          
	      RefineryUtilities.centerFrameOnScreen( chart );          
	      chart.setVisible( true ); 
	}
	
	public static void plot(int[] podatni, int[] chorzy, int[] odporni, int[] noweZachorowaniaKazdegoDnia){
	      Plotter chart = new Plotter("Model SIR", "wykres w dniach", podatni, chorzy, odporni, noweZachorowaniaKazdegoDnia);
	      chart.pack( );          
	      RefineryUtilities.centerFrameOnScreen( chart );          
	      chart.setVisible( true ); 
	}
	
	
	private XYDataset createDataset( int[] podatni, int[] chorzy, int[] odporni ){
	      final XYSeries S = new XYSeries( "Podatni" );   
	      final XYSeries I = new XYSeries( "Chorzy" ); 
	      final XYSeries R = new XYSeries( "Odporni" );  
	      for(int i=0; i<podatni.length; i++){
	    	  S.add( i, podatni[i] );
	    	  I.add( i, chorzy[i] );
	    	  R.add( i, odporni[i] );
	      }         
	      final XYSeriesCollection dataset = new XYSeriesCollection( );          
	      dataset.addSeries( S );          
	      dataset.addSeries( I );          
	      dataset.addSeries( R );
	      return dataset;
	}
	
	private XYDataset createDataset( int[] podatni, int[] chorzy, int[] odporni, int[] noweZachorowaniaKazdegoDnia ){
	      final XYSeries S = new XYSeries( "Podatni" );   
	      final XYSeries I = new XYSeries( "Chorzy" ); 
	      final XYSeries R = new XYSeries( "Odporni" );  
	      final XYSeries zachorowania = new XYSeries( "Nowe zachorowania" );
	      for(int i=0; i<podatni.length; i++){
	    	  S.add( i, podatni[i] );
	    	  I.add( i, chorzy[i] );
	    	  R.add( i, odporni[i] );
	    	  zachorowania.add( i, noweZachorowaniaKazdegoDnia[i] );
	      }         
	      final XYSeriesCollection dataset = new XYSeriesCollection( );          
	      dataset.addSeries( S );          
	      dataset.addSeries( I );          
	      dataset.addSeries( R );
	      dataset.addSeries(zachorowania);
	      return dataset;
	}
	

}
