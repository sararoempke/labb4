/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author moawahlgren
 */
public class Histogram {

public void displayHistogram(int[][] matrix){
     
        final NumberAxis xAxel = new NumberAxis();
        final NumberAxis yAxel = new NumberAxis();
        final LineChart lineChart = new LineChart(xAxel, yAxel);
        lineChart.setTitle("Histogram");
        
        /**/
        //defining series
        
        XYChart.Series seriesRed = new XYChart.Series();
        XYChart.Series seriesGreen = new XYChart.Series();
        XYChart.Series seriesBlue = new XYChart.Series();
        
        seriesRed.setName("Red");
        seriesGreen.setName("Green");
        seriesBlue.setName("Blue");
        
     
        
            
                for (int j=0; j<matrix[0].length; j++) {
                    // lägga data i series
                    
                    seriesRed.getData().add(new XYChart.Data(j, matrix[0][j]));
                    seriesGreen.getData().add(new XYChart.Data(j, matrix[1][j]));
                    seriesBlue.getData().add(new XYChart.Data(j, matrix[2][j]));
                   
                }
        
            
        
        
        
    }
   
    
}
