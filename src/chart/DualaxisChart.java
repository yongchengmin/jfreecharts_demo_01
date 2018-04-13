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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleEdge;

import util.ChartUtils;
import util.Serie;

/**
 * 
 * @author ccw
 * @date 2014-6-11
 *       <p>
 *       2��Y��ͼ��<br/>
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

public class DualaxisChart {

	public static JFreeChart createChart() {
		String[] categories = { "1999-12-31", "2000-12-31", "2001-12-31", "2002-12-31", "2003-12-31", "2004-12-31", "2005-12-31", "2006-12-31", "2007-12-31",
				"2008-12-31", "2009-12-31", "2010-12-31", "2011-12-31", "2012-12-31", "2013-12-31" };
		for (int i = 0; i < categories.length; i++) {
			categories[i]=categories[i].substring(0, 4);
		}
		Vector<Serie> seriesNetProfit = new Vector<Serie>();
		// ������
		Object[] netProfit = { 92669.20, 95790.47, 106187.80, 128530.88, 156608.82, 193003.08, 255800.38, 335302.66, 549877.54, 1251596.81, 1321658.11,
				1917721.09, 2728598.10, 3418600.00, 4092200.00 };
		// ����֧����
		Object[] payoutRatio = { "39.01", "--", "45.39", "30.46", "27.50", "24.34", "19.90", "19.48", "12.67", "10.40", "10.02", "11.97", "20.51", "30.01",
				" --" };
		seriesNetProfit.add(new Serie("������", netProfit));

		Vector<Serie> seriesPayoutRatio = new Vector<Serie>();
		seriesPayoutRatio.add(new Serie("����֧����", payoutRatio));

		DefaultCategoryDataset datasetNetProfit = ChartUtils.createDefaultCategoryDataset(seriesNetProfit, categories);
		JFreeChart chart = ChartFactory.createBarChart("", "", "������(��Ԫ)", datasetNetProfit);
		ChartUtils.setAntiAlias(chart);// �����
		ChartUtils.setBarRenderer(chart.getCategoryPlot(), false);
		// ����������
		ChartUtils.setXAixs(chart.getCategoryPlot());
		ChartUtils.setYAixs(chart.getCategoryPlot());
		// linechart
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		DefaultCategoryDataset datasetPayoutRatio = ChartUtils.createDefaultCategoryDataset(seriesPayoutRatio, categories);
		categoryplot.setDataset(1, datasetPayoutRatio);
		categoryplot.mapDatasetToRangeAxis(1, 1);
		// X��̶�
		CategoryAxis categoryaxis = categoryplot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		// �Ҳ�Y��
		NumberAxis numberaxis = new NumberAxis("����֧����(%)");
		categoryplot.setRangeAxis(1, numberaxis);
		// ����Y�̶�
		numberaxis.setAxisLineVisible(false);
		numberaxis.setTickMarksVisible(false);

		// ��������ͼ��ʽ
		LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
		lineRenderer.setSeriesPaint(0, new Color(255, 185, 1));
		lineRenderer.setBaseShapesVisible(true);// ���ݵ������״
		categoryplot.setRenderer(1, lineRenderer);
		
		categoryplot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);// ����Z-index, ������ͼ��ǰ��
		chart.getLegend().setPosition(RectangleEdge.TOP);//��ע�ڶ���
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
				ChartPanel chartPanel = new DualaxisChart().getChartPanel();
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
    public DualaxisChart(){
    	chart = createChart();
    	frame1=new ChartPanel(chart,true);
    }
	
    public static void outPng() throws IOException{
    	//ͼƬ���ļ���ʽ��,��Ҫ�õ�FileOutputStream�������.
    	 OutputStream os = new FileOutputStream("DualaxisChart.jpeg");
    	//ʹ��һ������application�Ĺ�����,��chartת����JPEG��ʽ��ͼƬ.��3�������ǿ��,��4�������Ǹ߶�.
         ChartUtilities.writeChartAsJPEG(os, new DualaxisChart().getJFreeChart(), 1024, 420);
         os.close();//�ر������
    }
}
