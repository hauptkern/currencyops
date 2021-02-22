package currencyops;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.SpinnerNumberModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.awt.Canvas;
import javax.swing.ListSelectionModel;
import java.awt.Scrollbar;
import java.awt.Panel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
/**
  import org.json.JSONArray;  these libraries will be used in the future.
  import org.json.JSONObject;
 */
public class Currencyops extends Thread {

	private JFrame frmCurrencyops;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Currencyops window = new Currencyops();
					window.frmCurrencyops.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Currencyops() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public String timetoreadable(long unixtime) {
		TimeZone tz = TimeZone.getTimeZone("GMT+0300");
		Calendar cal = Calendar.getInstance(tz);
		cal.setTimeInMillis(unixtime);
		SimpleDateFormat f = new SimpleDateFormat("HH:mm");
		f.setTimeZone(tz);
		return (String) f.format(cal.getTime());
	}
	public int addArrayToList(String jsonString,JList selectedList) {
		String[] splited;
		jsonString=jsonString.replace("callback([", "");
	    jsonString=jsonString.replace("]);", "");
		splited=jsonString.split("]");
		splited[0]=splited[0].replace("[", "");
		DefaultListModel model1 = new DefaultListModel();
		for(int i=Array.getLength(splited)-1;i>=0;i--) {
			String currencydata[]=splited[i].replace(",[","").split(",");
			model1.addElement(currencydata[1]+ " "+ timetoreadable(Long.parseLong(currencydata[0].replace("[", ""))));
		}
		selectedList.setModel(model1);
		return 0;
	}
	public void updatelist(String urllink, JList selectedList) {
		HttpURLConnection connection = null;
	    String output="";
	    try {
	        URL url = new URL(urllink);
	        connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod("GET");
	        connection.setRequestProperty("Content-Type", "application/json");
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (connection.getInputStream()), "UTF-8"));
	        output = br.lines().collect(Collectors.joining());
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (ProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if(connection!=null){
	            connection.disconnect();
	        }
	    }
	    addArrayToList(output,selectedList);
	}
	private void initialize() {
		frmCurrencyops = new JFrame();
		frmCurrencyops.setIconImage(Toolkit.getDefaultToolkit().getImage(Currencyops.class.getResource("/img/icon.png")));
		frmCurrencyops.setResizable(false);
		frmCurrencyops.setTitle("Currencyops");
		frmCurrencyops.setBounds(100, 100, 509, 307);
		frmCurrencyops.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCurrencyops.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 507, 279);
		tabbedPane.setBorder(null);
		frmCurrencyops.getContentPane().add(tabbedPane);
		
		JPanel observer = new JPanel();
		tabbedPane.addTab("Observer", null, observer, null);
		observer.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("EUR/USD");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 12, 136, 15);
		observer.add(lblNewLabel);
		
		JLabel lblBtcusd = new JLabel("BTC/USD");
		lblBtcusd.setHorizontalAlignment(SwingConstants.CENTER);
		lblBtcusd.setBounds(160, 12, 170, 15);
		observer.add(lblBtcusd);
		
		JLabel lblUsdtry = new JLabel("USD/TRY");
		lblUsdtry.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsdtry.setBounds(342, 12, 136, 15);
		observer.add(lblUsdtry);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 28, 136, 194);
		observer.add(scrollPane);
		
		JList first = new JList();
		scrollPane.setViewportView(first);
		first.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(160, 28, 170, 194);
		observer.add(scrollPane_1);
		
		JList second = new JList();
		scrollPane_1.setViewportView(second);
		second.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setBounds(342, 28, 136, 194);
		observer.add(scrollPane_1_1);
		
		JList third = new JList();
		scrollPane_1_1.setViewportView(third);
		third.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JPanel ops_settings = new JPanel();
		tabbedPane.addTab("Settings", null, ops_settings, null);
		ops_settings.setLayout(null);
		
		JLabel lblUpdateInterval = new JLabel("Update interval:");
		lblUpdateInterval.setBounds(12, 12, 130, 15);
		ops_settings.add(lblUpdateInterval);
		
		JSpinner spinner = new JSpinner();
		spinner.setEnabled(false);
		spinner.setModel(new SpinnerNumberModel(new Integer(1000), new Integer(999), null, new Integer(1)));
		spinner.setBounds(133, 10, 73, 20);
		ops_settings.add(spinner);
		
		JLabel lblMs = new JLabel("ms");
		lblMs.setBounds(212, 12, 70, 15);
		ops_settings.add(lblMs);
		
		JLabel setting_warning = new JLabel("This tab contains experimental settings for testing.");
		setting_warning.setHorizontalAlignment(SwingConstants.LEFT);
		setting_warning.setVerticalAlignment(SwingConstants.TOP);
		setting_warning.setBounds(12, 204, 478, 20);
		ops_settings.add(setting_warning);
		
		JLabel disabledwarning = new JLabel("Disabled until stable release.");
		disabledwarning.setBounds(12, 225, 367, 15);
		ops_settings.add(disabledwarning);
		
		JButton btnSelectCurrencies = new JButton("Select Currencies");
		btnSelectCurrencies.setEnabled(false);
		btnSelectCurrencies.setBounds(332, 7, 158, 25);
		ops_settings.add(btnSelectCurrencies);
		
		JButton btnSetSources = new JButton("Set Sources");
		btnSetSources.setEnabled(false);
		btnSetSources.setBounds(332, 40, 158, 25);
		ops_settings.add(btnSetSources);
		
		JLabel lblTimezone = new JLabel("Timezone:");
		lblTimezone.setBounds(12, 39, 89, 15);
		ops_settings.add(lblTimezone);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setEnabled(false);
		textField.setBounds(92, 37, 114, 19);
		ops_settings.add(textField);
		textField.setColumns(10);
		
		JButton btnModifyAlerts = new JButton("Modify Alerts");
		btnModifyAlerts.setEnabled(false);
		btnModifyAlerts.setBounds(332, 74, 158, 25);
		ops_settings.add(btnModifyAlerts);
		
		JRadioButton rdbtnUtc = new JRadioButton("UTC");
		rdbtnUtc.setEnabled(false);
		rdbtnUtc.setBounds(212, 35, 59, 23);
		ops_settings.add(rdbtnUtc);
		
		JRadioButton rdbtnGmt = new JRadioButton("GMT");
		rdbtnGmt.setEnabled(false);
		rdbtnGmt.setBounds(212, 62, 59, 22);
		ops_settings.add(rdbtnGmt);
		
		JButton btnGraphSettings = new JButton("Graph Settings");
		btnGraphSettings.setEnabled(false);
		btnGraphSettings.setBounds(332, 109, 158, 25);
		ops_settings.add(btnGraphSettings);
		
		JLabel lblGmt = new JLabel("GMT+3");
		lblGmt.setBounds(12, 230, 70, 15);
		observer.add(lblGmt);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updatelist("https://tlkur.com/style/jsonp.php?url=http://tlkur.com/historical/data/EUR/USDdaily.json",first);
				updatelist("https://tlkur.com/style/jsonp.php?url=http://tlkur.com/historical/data/BTC/USDdaily.json",second);
				updatelist("https://tlkur.com/style/jsonp.php?url=http://tlkur.com/historical/data/USD/TLdaily.json",third);
			}
		});
		btnRefresh.setBounds(342, 225, 136, 20);
		observer.add(btnRefresh);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("About", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Currencyops.class.getResource("/img/icon.png")));
		lblNewLabel_1.setBounds(196, 12, 128, 107);
		panel.add(lblNewLabel_1);
		
		JLabel lblCurrencyops = new JLabel("Currencyops");
		lblCurrencyops.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrencyops.setBounds(196, 131, 128, 15);
		panel.add(lblCurrencyops);
		
		JLabel lblSimpleCurrencyMonitor = new JLabel("Simple currency exchange rates monitor.");
		lblSimpleCurrencyMonitor.setHorizontalAlignment(SwingConstants.CENTER);
		lblSimpleCurrencyMonitor.setBounds(91, 158, 362, 15);
		panel.add(lblSimpleCurrencyMonitor);
		
		JLabel lblGithubhauptkern = new JLabel("github@hauptkern");
		lblGithubhauptkern.setBounds(12, 225, 154, 15);
		panel.add(lblGithubhauptkern);
		
		JLabel lblHauptkernpw = new JLabel("http://hauptkern.pw");
		lblHauptkernpw.setBounds(336, 225, 154, 15);
		panel.add(lblHauptkernpw);
		
		JLabel lblLicensedUnderThe = new JLabel("Licensed under the GNU General Public License v2");
		lblLicensedUnderThe.setBounds(91, 185, 362, 15);
		panel.add(lblLicensedUnderThe);
		
		updatelist("https://tlkur.com/style/jsonp.php?url=http://tlkur.com/historical/data/EUR/USDdaily.json",first);
		updatelist("https://tlkur.com/style/jsonp.php?url=http://tlkur.com/historical/data/BTC/USDdaily.json",second);
		updatelist("https://tlkur.com/style/jsonp.php?url=http://tlkur.com/historical/data/USD/TLdaily.json",third);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
