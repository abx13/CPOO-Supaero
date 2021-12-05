

package Modelconso;

import simulation.Temps;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

public class LineChartEx extends JFrame {
    
    
   


  

    public LineChartEx(double[] tableau_Puissance, String x, String y, String titre) {
        

        initUI(tableau_Puissance, x, y, titre);
    }

    private void initUI(double[] tableau_Puissance, String x, String y, String titre) {
        

        XYDataset dataset = createDataset(tableau_Puissance);

        
        JFreeChart chart = createChart(dataset, x, y, titre);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset(double[] tableau_Puissance) {

        var series = new XYSeries("");
        if (Param.display_day) {
            for (int t = 0; t < Temps.NBMINUTESJOUR; t++) {
                series.add(t, tableau_Puissance[t]);
                }
                            

        }
        if (!Param.display_day) {
            for (int t = 0; t < Temps.NBJOURSANNEE; t++) {
                series.add(t, tableau_Puissance[t]);

            }

        }

   

        var dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset, String x, String y, String titre) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "titre",
                x,
                y,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer(true,false); // pour changer avec ou sans points
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle(titre,
                        new Font("Serif", java.awt.Font.BOLD, 22)
                )
        );

        return chart;
    }

    public static void main(String[] args) {
        FonctionAbstraites fonction = new FonctionAbstraites();
        Operations op = new Operations();
        double[] tableau = fonction.sinusoide(1.3, 800);
        op.ecretage(tableau, 1);
        op.seulement_positif(tableau);
        
        String legende_X = "temps en min";
        String legende_Y = "Puissance";
        String titre = "Sinusoide";
    

        EventQueue.invokeLater(() -> {

            var ex = new LineChartEx(tableau,legende_X, legende_Y, titre);
            ex.setVisible(true);
            
        });
    }
}