
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

class 鏃ュ巻璁颁簨鏈� extends JFrame implements ActionListener{
	int N = 20;
	 ArrayList<String> ifsave = new ArrayList<String> ();
	final String monthlist[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	private static final int YEAR_RANGE = 50; // 骞翠唤鑼冨洿锛屽嵆寰�鍓嶅線鍚庡悇鎺ㄥ灏戝勾
	String Yearlist[] = {"浠婂勾","鏄庡勾"};
	String yearcommand[] = {"<",">"};         //鍙湁鍙棤
	String monthcommand[] = {"<-","->"};      //鍙湁鍙棤
	String monthdays[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16",
			"17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};//31鏈�澶ф湀鏃ユ暟
	
	//鎹㈠勾
	JComboBox year = new JComboBox() ;//骞翠笅鎷夊垪琛�
	JButton yearleft = new JButton("<");
	JButton yearright = new JButton(">");
					
	//鎹㈡湀
	JComboBox<String> month = new JComboBox<String>(monthlist) ;//鏈堜笅鎷夊垪琛�
	JButton monthleft = new JButton("<-");
	JButton monthright = new JButton("->");
					
	//鍏朵粬鎸夐挳
	JButton today = new JButton("褰撳墠鏃ユ湡");
	JButton detail = new JButton("璁颁簨鍒楄〃");
	JButton back = new JButton("杩斿洖鏃ュ巻");
	JButton ring = new JButton("璁剧疆鎻愰啋");
	JButton save = new JButton("淇濆瓨");
	
	//鏃ユ湡鎸夐挳
	private JButton D[] = new JButton[monthdays.length];
	{
	for(int i=0;i<monthdays.length;i++)
	D[i] = new JButton(monthdays[i]);
	}
	
	//JFrame瀹炰緥鍖�
	JFrame f = new JFrame("鏃ュ巻璁颁簨鏈�");
	
	//闈㈡澘
	JPanel totalpanel = new JPanel();
	JPanel West = new JPanel();
	JPanel North = new JPanel();
	JPanel East = new JPanel();
	JPanel rili = new JPanel();
	JPanel Week = new JPanel();
	JPanel Button = new JPanel();
	JPanel list = new JPanel();
	JPanel titlelist = new JPanel();
	JPanel detailpanel = new JPanel();
	
	//鏂囨湰妗�
	JTextField daily = new JTextField("褰撳墠鎵�閫夋棩鏈�:");//璁颁簨鏈棩鏈熸潯
	JTextArea dailyText = new JTextArea("Strat Writing");//璁颁簨鏈�
	JTextField title = new JTextField("璁颁簨鍒楄〃");
	
	//鎽樺綍
	private Calendar cal;
	private DateFormat df = new SimpleDateFormat("yyyy骞碝M鏈�");
	/*2銆乯ava.text.DateFormat锛堟娊璞＄被锛�
       DateFormat 鏄棩鏈�/鏃堕棿鏍煎紡鍖栧瓙绫荤殑鎶借薄绫伙紝瀹冧互涓庤瑷�鏃犲叧鐨勬柟寮忔牸寮忓寲骞跺垎鏋愭棩鏈熸垨鏃堕棿銆�
       鏃ユ湡/鏃堕棿鏍煎紡鍖栧瓙绫伙紙濡� SimpleDateFormat锛夊厑璁歌繘琛屾牸寮忓寲锛堜篃灏辨槸鏃ユ湡 -> 鏂囨湰锛夈�佸垎鏋愶紙鏂囨湰-> 鏃ユ湡锛夊拰鏍囧噯鍖栥��
       灏嗘棩鏈熻〃绀轰负 Date 瀵硅薄锛屾垨鑰呰〃绀轰负浠� GMT锛堟牸鏋楀凹娌绘爣鍑嗘椂闂达級1970 骞达紝1 鏈� 1 鏃� 00:00:00 杩欎竴鍒诲紑濮嬬殑姣鏁般��

	3銆乯ava.text.SimpleDateFormat锛圖ateFormat鐨勭洿鎺ュ瓙绫伙級
       SimpleDateFormat 鏄竴涓互涓庤瑷�鐜鐩稿叧鐨勬柟寮忔潵鏍煎紡鍖栧拰鍒嗘瀽鏃ユ湡鐨勫叿浣撶被銆傚畠鍏佽杩涜鏍煎紡鍖栵紙鏃ユ湡 -> 鏂囨湰锛夈�佸垎鏋愶紙鏂囨湰 -> 鏃ユ湡锛夊拰瑙勮寖鍖栥��
  	SimpleDateFormat 浣垮緱鍙互閫夋嫨浠讳綍鐢ㄦ埛瀹氫箟鐨勬棩鏈�-鏃堕棿鏍煎紡鐨勬ā寮忋�備絾鏄紝浠嶇劧寤鸿閫氳繃 DateFormat 涓�
      鐨� getTimeInstance銆乬etDateInstance 鎴� getDateTimeInstance 鏉ユ柊鐨勫垱寤烘棩鏈�-鏃堕棿鏍煎紡鍖栫▼搴忋��
       */
	private JLabel dateLabel;
	private static final Calendar NOW = Calendar.getInstance();
	
	//Color
	private static final int DATE_GRAY = -1;
   private static final int DATE_RED = 1;
   private static final int DATE_BLACK = 0;
   private static final Color FONT_GRAY = new Color(0xaa, 0xaa, 0xaa);
   
   //鎸夐挳鏀瑰彉涓嬫媺鑿滃崟褰撳墠鏄剧ず
   int a = 50;
	int m = NOW.get(Calendar.MONTH);
	
	//璁颁簨鍒楄〃//闇�鍒濆鍖栵紒
	JLabel ID[] = new JLabel[N];
	JLabel riqi[] = new JLabel[N];
	JLabel tixing[] = new JLabel[N];
	JLabel zhaiyao[] = new JLabel[N];
	
	//璁颁簨鏈ご鏉�
	int ri;
	
	//璁剧疆鎻愰啋鐨勬椂闂磋褰�
	int Year,Month,Day;//骞�,鏈�,鏃�
   
	//鏋勯�犳柟娉曡幏鍙栨棩鍘�
   public 鏃ュ巻璁颁簨鏈�() {
   	cal = Calendar.getInstance();//鏃ュ巻瀵硅薄
   	dateLabel = new JLabel(df.format(cal.getTime()));//鏃ュ巻琛�=================================
   	cal.setFirstDayOfWeek(Calendar.SUNDAY);//璁惧畾浠ユ槦鏈熸棩涓烘棩鍘嗙殑绗竴鍒�
   	isTodayring();       //鐪嬬湅浠婂ぉ鏈夋病鏈夋彁閱�
   }
	
   //闈㈡澘鍑芥暟
	void launch(){
		
		//
		for(int i=0;i<N;i++) {
			ID[i] = new JLabel();
		 	riqi[i] = new JLabel();
		 	tixing[i] = new JLabel();
		 	zhaiyao[i] = new JLabel();
		}
		
	
		Label b2 = new Label("M");
		Label b3 = new Label("T");
		Label b4 = new Label("W");
		Label b5 = new Label("T");
		Label b6 = new Label("F");
		Label b7 = new Label("S");
		Label b1 = new Label("S");
		
		//rili闈㈡澘
		rili.setLayout(new GridLayout(0,7));
		//rili.setBounds(0, 50, 650, 50);
		
		//North闈㈡澘
		North.setLayout(new BorderLayout());
		North.setBounds(0, 0, 1000, 50);
		North.setBackground(Color.white);
		
		//璋冩帶骞存湀鎸夐挳鍙婂叾浠�
		Button.add(yearleft);Button.add(year);Button.add(yearright);
		Button.add(monthleft);Button.add(month);Button.add(monthright);
		Button.add(today);Button.add(detail);
		Button.setBackground(Color.white);
		North.add("West",Button);
		
		//West闈㈡澘
		West.setBounds(0, 100, 650, 450);
		West.setLayout(new BorderLayout());
		West.setBackground(Color.lightGray);
		
		//鏄熸湡鏉�
		Week.setBounds(0, 50, 650, 50);
		Week.setLayout(new GridLayout(1,7));
		Week.setBackground(Color.WHITE);
		Week.add(b1);Week.add(b2);Week.add(b3);Week.add(b4);
		Week.add(b5);Week.add(b6);Week.add(b7);
		totalpanel.add(Week);
		
		
		//East闈㈡澘
		//璁颁簨鏈�
		East.setBounds(670, 50, 330, 450);
		East.setLayout(new BorderLayout());
		dailyText.setLineWrap(true);//鑷姩鎹㈣
		dailyText.setFont(new Font("瀹嬩綋",Font.PLAIN,22));
		dailyText.setEditable(true);    // 鍏佽淇敼缁撴灉鏂囨湰妗�
		dailyText.setBackground(Color.WHITE);
		
		//淇濆瓨鍜岃缃彁閱掓寜閽殑娣诲姞
		JPanel Saveandring = new JPanel();         
		Saveandring.setBounds(670, 500, 330, 30);
		Saveandring.setLayout(new BorderLayout());
		ring.setBackground(Color.WHITE);
		save.setBackground(Color.WHITE);
		Saveandring.add("West",ring);
		Saveandring.add("Center",save);
		totalpanel.add(Saveandring);
		
		//璁颁簨鏈棩鏈熸樉绀�
		daily.setBounds(670, 10, 330, 00);
		daily.setFont(new Font("瀹嬩綋",Font.PLAIN,20)); //瀛椾綋璁剧疆
		daily.setHorizontalAlignment(JTextField.LEFT);// 鏂囨湰妗嗕腑鐨勫唴瀹归噰鐢ㄥ乏瀵归綈鏂瑰紡
		daily.setEditable(false);    // 涓嶅厑璁镐慨鏀圭粨鏋滄枃鏈
		daily.setBackground(Color.lightGray);
		
		//璁颁簨鏈爮鐩坊鍔�
		East.setLayout(new BorderLayout());
		East.add("Center", dailyText);
		East.add("North",daily);
		
		//totalpanel鎬婚潰鏉�
		totalpanel.setLayout(new BorderLayout(3,3));
		totalpanel.add("North",North);
		totalpanel.add("West",West);
		totalpanel.add("East",East);
		
		//=============================================
		//鑾峰彇褰撳墠鏃堕棿骞舵樉绀�	鏉ユ簮  https://blog.csdn.net/qq_40866897/article/details/80823248
		Timer time1=new Timer();
		TimerTask task=new TimerTask() {//璁剧疆瀹氭椂鍣紝鍦ㄧ晫闈腑鏄剧ず鏃堕棿
			public void run() {
				long timemillis = System.currentTimeMillis(); 
				JTextField j = new JTextField();
				j.setBounds(670,0,330,50);
				f.add(j);
              SimpleDateFormat df = new SimpleDateFormat("yyyy骞碝M鏈坉d鏃�      HH:mm:ss  E");  	
              j.setText("            褰撳墠鏃堕棿锛�    "+df.format(new Date(timemillis)));
              j.setBackground(Color.white);
              j.setEditable(false);
			} 
		};
		time1.schedule(task, 0,1000);//涓�绉掗挓鍚庡紑濮嬫墽琛岋紝姣忚繃涓�绉掓墽琛屼竴娆�
		//=============================================
		
		//涓烘寜閿坊鍔犵洃鍚櫒
		//涓嬫媺
		year.addActionListener(this);
		month.addActionListener(this);
		
		//鍚勭鎸夐挳
		yearright.addActionListener(this);
		yearleft.addActionListener(this);
		monthright.addActionListener(this);
		monthleft.addActionListener(this);
		today.addActionListener(this);
		detail.addActionListener(this);
		ring.addActionListener(this);
		save.addActionListener(this);
		back.addActionListener(this);
		
		//鏃ユ湡鎸夐挳//鐐瑰嚮杩涘叆璁颁簨
		for(int i=0;i<monthdays.length;i++)
			D[i].addActionListener(this);
		
		
		
		//骞翠笅鎷夐�夋嫨
		int currentYear = cal.get(Calendar.YEAR);
		 for(int i = currentYear - YEAR_RANGE; i < currentYear + YEAR_RANGE + 1; i ++) {
	            year.addItem(i);
	        }
		year.addItemListener(new ItemListener() {
           public void itemStateChanged(ItemEvent e) {
               if(ItemEvent.SELECTED == e.getStateChange()) {
                   reset(); // 鍒锋柊甯冨眬
               }
           }
       });
       year.setSelectedIndex(YEAR_RANGE); // 榛樿閫変腑褰撳墠骞翠唤
       
       //鏈堜笅鎷夐�夋嫨
       month.addItemListener(new ItemListener() {
           public void itemStateChanged(ItemEvent e) {
               if(ItemEvent.SELECTED == e.getStateChange()) {
                   reset(); //鍒锋柊甯冨眬
               }
           }
       });
       month.setSelectedIndex(NOW.get(Calendar.MONTH)); // 閫夋嫨褰撳墠鏈堜唤       //==========锛燂紵NOW鍙敤锛宑al涓嶅彲鐢�   //cal鑾峰彇鏃堕棿閿欒
       
       //璁颁簨鍒楄〃琛ㄥご//璁颁簨鍒楄〃鐨勫叾浠栫紪鎺掑湪鍏舵寜閽鐐瑰嚮鏃剁紪鎺�
	 	titlelist.setBounds(0,50,650,50);
	 	titlelist.setLayout(new GridLayout(0,4));
	 	JLabel ID1 = new JLabel("ID");
	 	JLabel riqi1 = new JLabel("鏃ユ湡");
	 	JLabel tixing1 = new JLabel("鎻愰啋");
	 	JLabel zhaiyao1 = new JLabel("鎽樿");
	 	titlelist.add(ID1);titlelist.add(riqi1);titlelist.add(tixing1);titlelist.add(zhaiyao1);
       
		//鏁翠綋甯冨眬
       totalpanel.setBackground(Color.white);
		f.setContentPane(totalpanel);
		West.add("Center",rili);
		
		//Frame
		f.setSize(1000 , 600);//璁剧疆妗嗘灦澶у皬
		f.setLayout(null);//绌哄竷灞�
		f.setResizable(true);//涓嶅厑璁告敼鍙樻鏋跺ぇ灏�
		f.setVisible(true);//鍙
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//鍙叧
	}
	
	
	 public void actionPerformed(ActionEvent e) {
		 int i = 1;
		 
		 String label = e.getActionCommand();
		 if (label.equals(yearcommand[0]) && a>0) {
	            // 鐢ㄦ埛鎸変簡骞寸殑"<"閿�
		        year.setSelectedIndex(--a);
	        } 
		 
		 if (label.equals(yearcommand[1]) && a<100) {
	            // 鐢ㄦ埛鎸変簡骞寸殑">"閿�
			 	year.setSelectedIndex(++a);
	        } 
		 
		 if (label.equals(monthcommand[0]) && m>0) {             
	            // 鐢ㄦ埛鎸変簡鏈堢殑"->"閿�
			 	month.setSelectedIndex(--m);
	        } 
		 
		 if (label.equals(monthcommand[1]) && m<11) {
	            // 鐢ㄦ埛鎸変簡鏈堢殑"->"閿�
			 	month.setSelectedIndex(++m);
	        }
		 
		 for(i=0;i<monthdays.length;i++)	
		 	if (label.equals( monthdays[i] )) {
	            // 鐢ㄦ埛鎸変簡鏈堢殑"鏃ユ湡"閿�
			 	daily.setText("褰撳墠鎵�閫夋棩鏈�:鏈湀"+(i+1)+"鏃�");
			 	ri = i+1;
			 
	        }
		 if(label.equals("璁剧疆鎻愰啋")) {                 
			  
			 //鏉ユ簮 https://www.cnblogs.com/jiangxiulian/p/5961990.html
			 //鐐瑰嚮纭畾鍚� 鍦ㄤ笅鎷夎彍鍗曚腑鎵�閫夋嫨鐨勯�夐」浼氳璧嬪�肩粰 selectedyear; selectedmonth; selectedday;
			 
			 //涓嬫媺瀵硅瘽妗�
			 Object selectedyear = JOptionPane.showInputDialog(null, "璇烽�夋嫨骞�", "璁剧疆鎻愰啋", 
			 JOptionPane.INFORMATION_MESSAGE, null, 
			 Yearlist, Yearlist[0]);
			 for(i=NOW.get(Calendar.YEAR);i< (NOW.get(Calendar.YEAR) + 2);i++)
			    if( selectedyear.toString().equals(i) )    //equals()瀹岀編鐨勮В鍐充簡 object鍜宨nt 姣旇緝鐨勯棶棰�
			    	Year = i;
			 
			 Object selectedmonth = JOptionPane.showInputDialog(null, "璇烽�夋嫨鏈�", "璁剧疆鎻愰啋", 
			 JOptionPane.INFORMATION_MESSAGE, null, 
			 monthlist, monthlist[0]);
			 for(i=1;i<= monthlist.length;i++)
				    if( selectedmonth.toString().equals(i) )
				    	Month = i;
			 
			 Object selectedday = JOptionPane.showInputDialog(null, "璇烽�夋嫨鏃�", "璁剧疆鎻愰啋", 
			 JOptionPane.INFORMATION_MESSAGE, null, 
			 monthdays, monthdays[0]);
			 for(i=1;i<= 31;i++)
				    if( selectedday.toString().equals(i) )
				    	Day = i;
			 
			 //闇�鎻愰啋鑷姩淇濆瓨
			 	//淇濆瓨
				 for(int j=1;j<=monthdays.length;j++)
					 if(j == ri) {
						 System.out.println("Save success");
						 String yearmonth=dateLabel.getText();//灏嗚淇濆瓨鐨勫勾鏈堟棩浣滀负鏂囦欢鍚�
						 String day = ""+(j);
						 String str = yearmonth + day + "鏃� "+" (鎻愰啋)";//瑕佹彁閱掔殑鏂囦欢锛屾枃浠跺悕闄勪笂"鎻愰啋"瀛楁牱 
						 File inFile = new File("E:\\shixunrili\\",str);
						 FileWriter fw;
						 try {
							 fw = new FileWriter(inFile,true);
							 String ss=dailyText.getText();
							 fw.write(ss);
							 fw.close();
						 } catch (IOException e1) {
							 e1.printStackTrace();
						 }	
						 D[j-1].setBackground(Color.YELLOW);
						 ifsave.add(str);
					 }
				 
		 }
		 
		 //涓嶉渶鎻愰啋鐨�
		 if (label.equals("淇濆瓨")) {
		 		//鐢ㄦ埛鎸変簡"淇濆瓨"閿�
			 for(int j=1;j<=monthdays.length;j++)
				 if(j == ri) {
					 System.out.println("Save success");
					 String yearmonth=dateLabel.getText();//灏嗚淇濆瓨鐨勫勾鏈堟棩浣滀负鏂囦欢鍚�
					 String day = ""+(j);
					 String str = yearmonth + day + "鏃�.txt";
					 File inFile = new File("E:\\shixunrili\\",str);
					 FileWriter fw;
					 try {
						 fw = new FileWriter(inFile);
						 String ss=dailyText.getText();
						 fw.write(ss);
						 fw.close();
					 } catch (IOException e1) {
						 e1.printStackTrace();
					 }
					 D[j-1].setBackground(Color.YELLOW);
					 ifsave.add(str);
					 System.out.println(ifsave);
				 }
		 }
		 
		 if (label.equals("璁颁簨鍒楄〃")) {
	            // 鐢ㄦ埛鎸変簡鏈堢殑"璁颁簨鍒楄〃"閿�
			 int n=0;
			 //琛ㄥ悕
			 	title.setBounds(0 ,0 ,650 ,50);
			 	title.setHorizontalAlignment(JTextField.CENTER);// 鏂囨湰妗嗕腑鐨勫唴瀹归噰鐢ㄥ乏瀵归綈鏂瑰紡
			 	title.setFont(new Font("瀹嬩綋",Font.BOLD,20));
			 	title.setEditable(false);
			 //琛ㄥ唴瀹�
			 	list.setBounds(0, 100, 650, 450);
			 	list.setLayout(new GridLayout(0,4));
			 
			 //璁颁簨鍒楄〃鎬婚潰鏉�
			 	detailpanel.setBounds(0, 50, 650, 500);
			 	detailpanel.setLayout(null);
			 	detailpanel.add(title);
			 	detailpanel.add(list);
			 	detailpanel.add(titlelist);
			 	
			 //璇绘枃浠惰幏鍙栧垪琛ㄥ唴瀹�
			 	String path="E:\\shixunrili";
			 	getAllFileName(path);
			 
			 //灏嗘墍璇绘枃浠剁紪鎺�
			 	for(i=0;i<N;i++) {
			 		list.add(ID[i]);
			 		list.add(riqi[i]);
			 		list.add(tixing[i]);
			 		list.add(zhaiyao[i]);
			 	}
			
			 //鐣岄潰鍒锋柊
			 	//鎹㈤潰鏉�
			 	totalpanel.remove(West);
			 	totalpanel.remove(Week);
			 	
			 	Button.remove(detail);
			 	Button.add(back);
				
			 	totalpanel.add(detailpanel);
			 	
			 	totalpanel.updateUI();//鍒锋柊
	        }
		 
		 if (label.equals("杩斿洖鏃ュ巻")) {
			 	
			 //鐣岄潰鍒锋柊
			    //鎹㈤潰鏉�
			 	Button.remove(back);
			 	Button.add(detail);
			 	
			 	totalpanel.remove(detailpanel);
				totalpanel.add(West);
			 	totalpanel.add(Week);
			 	
			 	totalpanel.updateUI();//鍒锋柊
		 }
		 
		 
	 }
	 
	 /*        reset();creatData();isToday();鈫撯啌鈫撯啌鈫撯啌鈫撯啌鈫撯啌鍙栬嚜鈫撯啌鈫撯啌鈫撯啌鈫撯啌鈫撯啌鈫�
	  * https://zhidao.baidu.com/question/561169697228621404.html?fr=iks&word=
	 java%C8%D5%C0%FA%B1%ED%D4%F5%C3%B4%C8%C3%C8%D5%C0%FA%CA%B1%B6%D4%B5%C4&ie=gbk
	 */
	 private void reset() { // 姣忔骞翠唤鎴栨湀浠芥敼鍙樺悗鍒欐棩鍘嗛噸鏂版帓鍒�
		 	
	        int tyear = (Integer) year.getSelectedItem();
	        int tmonth = Integer.parseInt((String) month.getSelectedItem());
	        // 灏嗘棩鏈熻缃负鏈湀绗竴澶�
	        cal.set(Calendar.YEAR, tyear);
	        cal.set(Calendar.MONTH, tmonth -1);//tmonth鑾峰彇鐨勬槸12鏈堬紝浣咰alendar閲�11琛ㄧず12鏈堬紝0琛ㄧず1鏈�
	        cal.set(Calendar.DATE, 1);
	       
	        rili.removeAll(); // 娓呯┖鏃ュ巻鍒楄〃
	         
	        // 鍒ゆ柇鏈湀绗竴澶╂槸鏄熸湡鍑狅紝鍦ㄧ涓�澶╀箣鍓嶅鍔犵┖鐨勬棩鍘�
	        int startDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);  //鑾峰緱浠婂ぉ鏄懆鍑�//鍛ㄦ棩涓�1 鍛ㄥ叚涓�7
	        //System.out.println(cal.get(Calendar.DAY_OF_WEEK));
	        int i;
	        // 鍏堣缃负鏈懆灞炰簬涓婁釜鏈堢殑鍑犲ぉ锛岀疆鐏版樉绀�
	        	//灏嗗綋鍓嶆棩鏈熺Щ鍔ㄥ埌绗竴涓槦鏈熺殑鏄熸湡鏃�
	        cal.add(Calendar.DATE, -(startDayOfWeek - Calendar.SUNDAY) -1); // 0123456 //{1234567} //=========??? -stratDayOfWeek
	      
	        for(i = Calendar.SUNDAY; i < startDayOfWeek; i ++) {
	            cal.add(Calendar.DATE, 1);   //鐢辩涓�涓槦鏈熸棩鍚戝墠閫掑
	            int date = cal.get(Calendar.DATE); //寰楀埌閫掑鍚庣殑鏃ユ湡
	            rili.add(createDate(Integer.toString(date), DATE_GRAY)); //缃伆
	        }

	        // 鍒濆鍖栬繖涓湀閲岀殑鏃ユ湡
	        cal.set(Calendar.MONTH, tmonth - 1);
	        cal.set(Calendar.DATE, 1);
	        int maxDate = cal.getActualMaximum(Calendar.DATE); // 鏈湀鏈�澶氱殑澶╂暟锛堜笉鐢ㄥ啀鍘诲垽鏂槸鍚﹂棸骞达紝30锛�31锛�
	        i = 0;
	        for(int j = 1; j <= maxDate; j ++) {
	        	rili.add(D[i]); 
	        	D[i].setFont(new Font("瀹嬩綋", Font.BOLD, 30));
	        	D[i].setBackground(Color.lightGray);
	            cal.set(Calendar.DATE, j); // 鏃ユ湡涓�鐩磋嚜澧烇紝鐢ㄦ潵鍒ゆ柇鏄惁鏄粖澶�
	            if(isToday()) { // 鏄粖澶╁垯楂樹寒鏄剧ず
	            	D[i].setForeground(Color.RED);
	            } 
	         
	            else { // 涓嶆槸浠婂ぉ鍒欐樉绀轰负鏅�氶鑹�
	            	D[i].setForeground(Color.BLACK);
	            }
	           i++;
	        }
	        
	        // 鏈�鍚庝竴鍛ㄦ妸涓嶅睘浜庢湰鏈堢殑鍑犲ぉ鐢ㄧ伆鑹叉帶浠跺～鍏�
	        int lastDay = cal.get(Calendar.DAY_OF_WEEK); //鑾峰緱杩欎釜鏈堟渶鍚庝竴澶╁睘浜庢槦鏈熷嚑
	        //鐢变簬鍦ㄥ垵濮嬪寲鏈湀鏃ユ湡鐨勪唬鐮侀噷锛岃繍鐢╟al.set(Calendar.DATE, j);j++;鐨勪唬鐮侊紝鎵�浠al.get(Calendar.DAY_OF_WEEK);鎵�鑾峰彇鐨勬槸鏈湀鏈�鍚庝竴澶╃殑鏄熸湡鏁�
	        for(i = lastDay; i < Calendar.SATURDAY; i ++) {
	            cal.add(Calendar.DATE, 1); //鍚戝悗澧炲姞鏃ワ紝鐩磋嚦閬囧埌鏄熸湡鏃�
	            int date = cal.get(Calendar.DATE);//寰楀埌閫掑鍚庣殑鏃ユ湡
	            rili.add(createDate(Integer.toString(date), DATE_GRAY)); //缃伆
	        }
	        //rili.validate();
	    }
	 	    
	    // 鍒涘缓鏃ユ湡鎺т欢//璁剧疆瀛椾綋棰滆壊
	    private JLabel createDate(String date, int dateColor) {
	        JLabel label = new JLabel(date, JLabel.CENTER);
	        CompoundBorder border = new CompoundBorder(
	                new LineBorder(Color.WHITE, 1),
	                new EmptyBorder(10, 10, 10, 10));
	         
	        label.setBorder(border);
	        Font font = new Font("Arial", Font.BOLD, 30);
	        if(DATE_GRAY == dateColor) { // 濡傛灉涓嶆槸鏈湀鍒欐枃瀛楃疆鐏�
	            label.setForeground(FONT_GRAY);
	        } else if(DATE_RED == dateColor) { // 濡傛灉鏄粖澶╁垯楂樹寒鏄剧ず
	            label.setForeground(Color.RED);
	        }
	        label.setFont(font);
	        return label;
	    }

	    // 鍒ゆ柇鏄惁浠婂ぉ
	    private boolean isToday() {
	        return cal.get(Calendar.YEAR) == NOW.get(Calendar.YEAR)
	                && cal.get(Calendar.MONTH) == NOW.get(Calendar.MONTH)
	                && cal.get(Calendar.DATE) == NOW.get(Calendar.DATE);
	    }
	    
	    //鍒ゆ柇鎻愰啋鏃ユ湡鏄惁浠婂ぉ
	    private void isTodayring() {
	    	if(Year == NOW.get(Calendar.YEAR)
	           && Month == NOW.get(Calendar.MONTH)
	           && Day == NOW.get(Calendar.DATE)) {
	    		
	    		//鍒版湡鎻愰啋//鏉ユ簮 https://www.cnblogs.com/jiangxiulian/p/5961990.html
	    		Object[] options = { "鏄�" }; 
	    		JOptionPane.showOptionDialog(null, "浣犱粖澶╂湁璁剧疆鐨勬彁閱掑埌鏈�", "鎻愰啋", 
	    		JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, 
	    		null, options, options[0]);
	    	}
	    }
	    
	    //鑾峰彇鏂囦欢鍚嶅瓧//鏂囦欢澶逛笅鎵�鏈夋枃浠跺悕瀛�
	    // 鏂规硶鏉ユ簮 https://blog.csdn.net/yoyofu007/article/details/80994999
	    public  void getAllFileName(String path) {
	        //ArrayList<String> files = new ArrayList<String>();
	        //boolean flag = false;
	        File file = new File(path);
	        File[] tempList = file.listFiles();
	        BufferedReader reader = null; 

	        for (int i = 0; i < tempList.length; i++) {
	            if (tempList[i].isFile()) {
//	              System.out.println("鏂�     浠讹細" + tempList[i]);
	                //fileNameList.add(tempList[i].toString());
	            	if(tempList[i].getName().indexOf("鎻愰啋") != -1)//鍒ゆ柇鏂囦欢鍚嶆槸鍚﹀寘鍚�"鎻愰啋"瀛楁牱
	            		tixing[i].setText("鈭�");
	            	riqi[i].setText(tempList[i].getName());
	            	ID[i].setText(""+ (i+1));
	                System.out.println(tempList[i].getName());
	                
	                //璇绘枃浠�//浠ヨ涓哄崟浣嶈鍙栨枃浠跺唴瀹癸紝涓�娆¤涓�鏁磋
	                // 鏂规硶鏉ユ簮 https://www.cnblogs.com/fnlingnzb-learner/p/6011324.html
	    	        try {  
	    	            reader = new BufferedReader(new FileReader(tempList[i]));  
	    	            String tempString = null;  
	    	            int line = 1;  
	    	            // 涓�娆¤鍏ヤ竴琛岋紝鐩村埌璇诲叆null涓烘枃浠剁粨鏉�  
	    	            while ((tempString = reader.readLine()) != null) {  
	    	                // 鏄剧ず琛屽彿  
	    	                System.out.println("line " + line + ": " + tempString);  
	    	                zhaiyao[i].setText(tempString);
	    	                line++;  
	    	            }  
	    	            reader.close();  
	    	        } catch (IOException e) {  
	    	            e.printStackTrace();  
	    	        }
	            }
	            
	        }
	        return;
	    }
	    
	 
	public static void main(String[] args) {
		鏃ュ巻璁颁簨鏈� rili = new 鏃ュ巻璁颁簨鏈�();
		rili.launch();
	}
	
	
}
