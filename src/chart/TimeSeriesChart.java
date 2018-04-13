package chart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import util.ChartUtils;

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
public class TimeSeriesChart {

	/**
	 * �������ݼ���
	 * 
	 * @return
	 */
	public static TimeSeriesCollection createDataset() {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		String[] categories = { "�ɶ��ܻ���", "ǰʮ���ֹɱ���" };
	
		Random random = new Random();
		for (int row = 0; row < categories.length; row++) {
			Vector<Object[]> dateValues = new Vector<Object[]>();
			for (int i = 0; i < 20; i++) {
				String date = (2000 + i) + "-0" + i + "-21";
				System.out.println(date);
				Object[] dateValue = { date, random.nextInt(10) };
				dateValues.add(dateValue);

			}
			TimeSeries timeSeries = ChartUtils.createTimeseries(categories[row], dateValues);
			dataset.addSeries(timeSeries);
		}
		return dataset;
	}

	public static JFreeChart createChart() {
		// 2������Chart[������ͬͼ��]
		TimeSeriesCollection dataset = createDataset();
		JFreeChart chart = ChartFactory.createTimeSeriesChart("�ɶ��ܻ���", "", "", dataset);
		// 3:���ÿ���ݣ���ֹ������ʾ�����
		ChartUtils.setAntiAlias(chart);// �����
		// 4:�����ӽ�����Ⱦ[������ͬͼ��]
		ChartUtils.setTimeSeriesRender(chart.getPlot(), true, true);
		// 5:���������ֽ�����Ⱦ
		XYPlot xyplot = (XYPlot) chart.getPlot();
		ChartUtils.setXY_XAixs(xyplot);
		ChartUtils.setXY_YAixs(xyplot);
		// ����X������
		DateAxis domainAxis = (DateAxis) xyplot.getDomainAxis();
		domainAxis.setAutoTickUnitSelection(false);
		DateTickUnit dateTickUnit = null;
		if (dataset.getItemCount(0) < 16) {
			//�̶ȵ�λ��,����Ϊ���
			dateTickUnit = new DateTickUnit(DateTickUnitType.MONTH, 6, new SimpleDateFormat("yyyy-MM")); // �ڶ���������ʱ������
		} else {// ���ݹ���,����ʾ����
			XYLineAndShapeRenderer xyRenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
			xyRenderer.setBaseItemLabelsVisible(false);
			dateTickUnit = new DateTickUnit(DateTickUnitType.YEAR, 1, new SimpleDateFormat("yyyy")); // �ڶ���������ʱ������
		}
		// ����ʱ�䵥λ
		domainAxis.setTickUnit(dateTickUnit);
		ChartUtils.setLegendEmptyBorder(chart);
		// ����ͼ��λ��
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
				ChartPanel chartPanel = new TimeSeriesChart().getChartPanel();
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
    public TimeSeriesChart(){
    	chart = createChart();
    	frame1=new ChartPanel(chart,true);
    }
	
    public static void outPng() throws IOException{
    	//ͼƬ���ļ���ʽ��,��Ҫ�õ�FileOutputStream�������.
    	 OutputStream os = new FileOutputStream("TimeSeriesChart.jpeg");
    	//ʹ��һ������application�Ĺ�����,��chartת����JPEG��ʽ��ͼƬ.��3�������ǿ��,��4�������Ǹ߶�.
         ChartUtilities.writeChartAsJPEG(os, new TimeSeriesChart().getJFreeChart(), 1024, 420);
         os.close();//�ر������
    }
}
