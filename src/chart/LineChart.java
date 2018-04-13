package chart;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.data.category.DefaultCategoryDataset;

import util.ChartUtils;
import util.Serie;

/**
 * 
 * @author ccw
 * @date 2014-6-11
 *       <p>
 *       ����ͼ���裺<br/>
 *       1���������ݼ���<br/>
 *       2������Chart��<br/>
 *       3:���ÿ���ݣ���ֹ������ʾ�����<br/>
 *       4:�����ӽ�����Ⱦ��<br/>
 *       5:���������ֽ�����Ⱦ<br/>
 *       6:ʹ��chartPanel����<br/>
 * 
 *       </p>
 */
public class LineChart {

	public static DefaultCategoryDataset createDataset() {
		// ��ע���
		String[] categories = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		Vector<Serie> series = new Vector<Serie>();
		// �������ƣ��������е�ֵ����
		series.add(new Serie("Tokyo", new Double[] { 49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4 }));
		series.add(new Serie("New York", new Double[] { 83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3 }));
		series.add(new Serie("London", new Double[] { 48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2 }));
		series.add(new Serie("Berlin", new Double[] { 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1 }));
		// 1���������ݼ���
		DefaultCategoryDataset dataset = ChartUtils.createDefaultCategoryDataset(series, categories);
		return dataset;
	}

	public static JFreeChart createChart() {
		// 2������Chart[������ͬͼ��]
		JFreeChart chart = ChartFactory.createLineChart("Monthly Average Rainfall", "", "Rainfall (mm)", createDataset());
		// 3:���ÿ���ݣ���ֹ������ʾ�����
		ChartUtils.setAntiAlias(chart);// �����
		// 4:�����ӽ�����Ⱦ[[���ò�ͬ��Ⱦ]]
		ChartUtils.setLineRender(chart.getCategoryPlot(), false,true);//
		// 5:���������ֽ�����Ⱦ
		ChartUtils.setXAixs(chart.getCategoryPlot());// X��������Ⱦ
		ChartUtils.setYAixs(chart.getCategoryPlot());// Y��������Ⱦ
		// ���ñ�ע�ޱ߿�
		chart.getLegend().setFrame(new BlockBorder(Color.WHITE));
		return chart;
	}

	public ChartPanel getChartPanel(){
		// 6:ʹ��chartPanel����
		ChartPanel chartPanel = new ChartPanel(createChart());
		return chartPanel;
	}
	
	public static void main(String[] args) {
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 420);
		frame.setLocationRelativeTo(null);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// ����ͼ��
				ChartPanel chartPanel = new LineChart().getChartPanel();
				frame.getContentPane().add(chartPanel);
				frame.setVisible(true);
			}
		});
		
		/*try {
			outPng();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	static JFreeChart chart;
	public JFreeChart getJFreeChart(){
    	return chart;
    }
	ChartPanel frame1;
    public LineChart(){
    	chart = createChart();
    	frame1=new ChartPanel(chart,true);
    }
	
    public static void outPng() throws IOException{
    	//ͼƬ���ļ���ʽ��,��Ҫ�õ�FileOutputStream�������.
    	 OutputStream os = new FileOutputStream("LineChart.jpeg");
    	//ʹ��һ������application�Ĺ�����,��chartת����JPEG��ʽ��ͼƬ.��3�������ǿ��,��4�������Ǹ߶�.
         ChartUtilities.writeChartAsJPEG(os, new LineChart().getJFreeChart(), 1024, 420);
         os.close();//�ر������
    }
}
