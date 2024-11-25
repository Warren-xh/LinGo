import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Window {
	private static String _cabinetID;

	private static String _layerID;

	private static String _commodityPrice;

	private static String _chineseName;

	private static String[] _save = new String[4];

//	private static String[] arr;

//	private static boolean _newItem = false;

	private static int start = 0;

	public static ArrayList<String[]> synList = new ArrayList<String[]>();

	private static boolean checkedAll = false;

	private JButton syA,syB,syC,syD,syE,translation,insert;

	private JTextArea[] foreign;

	JLabel alarm;

	JTextArea textArea;

	public Window(String[] brr) {
		// 创建一个JFrame实例
        JFrame frame = new JFrame("AI Assistant Demo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(0, 0, 800, 600); // 设置窗口大小
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // 窗口居中显示

        frame.setLayout(null); // 无布局管理

        Container container = frame.getContentPane(); //创建容器对象

        JButton jButton1 = new JButton("Get Next One");
//        JButton jButton2 = new JButton("按钮2");

        jButton1.setBounds(10, 80,160,30);   //设置每个组件的位置和大小

        JLabel description = new JLabel("Please click on GetNextOne button and see the Chinese synonyms.");
        description.setBounds(10,0,780,100);
        description.setFont(new Font("Times New Roman", Font.BOLD, 18));

        if(synList.isEmpty()) {
    		checkedAll = true;
    	}
        alarm = new JLabel("Your new items have all been checked!");
        alarm.setBounds(220,45,780,100);
        if(checkedAll) {
        	alarm.setFont(new Font("Times New Roman", Font.BOLD, 18));
        }else {
        	alarm.setFont(new Font("Times New Roman", Font.BOLD, 0));
        }
        alarm.setForeground(Color.green);

        JLabel description2 = new JLabel("If you are disappointed about the words, you can click on the button to replace it.");
        description2.setBounds(150,80,800,100);
        description2.setFont(new Font("Times New Roman", Font.BOLD, 14));
        description2.setForeground(Color.DARK_GRAY);
        container.add(description2);

        JLabel description3 = new JLabel("If you click the GetNextOne button, the synonyms of this word are CONFIRMED. Please be careful!");
        description3.setBounds(90,220,800,100);
        description3.setFont(new Font("Times New Roman", Font.BOLD, 14));
        description3.setForeground(Color.RED);
        container.add(description3);

        JLabel description4 = new JLabel("Let's create the translation results!");
        description4.setBounds(10,250,800,100);
        description4.setFont(new Font("Times New Roman", Font.BOLD, 18));
//        description3.setForeground(Color.RED);
        container.add(description4);

        JLabel description5 = new JLabel("   English    Franch    Russian    Spanish    Arabic");
        description5.setBounds(300,340,800,100);
        description5.setFont(new Font("Times New Roman", Font.BOLD, 18));
//        description3.setForeground(Color.RED);
        container.add(description5);

        container.add(jButton1);
//        container.add(jButton2);
        container.add(description);
        container.add(alarm);

        insert = new JButton("Insert by Myself!");

        insert.setBounds(600,0,200,50);
        container.add(insert);

        // 展示这个词语
        if(start == 0)	{
			textArea = new JTextArea("牙刷");
		}else{
			textArea = new JTextArea();
		}
		textArea.setEditable(false); // 设置为不可编辑
		textArea.setFont(new Font("SimSun", Font.BOLD, 18));
		textArea.setBounds(200,80,450,30);
//        JScrollPane scrollPane = new JScrollPane(textArea);
        container.add(textArea);

        JPanel synButton = new JPanel(new GridLayout(1,5));

        syA = new JButton(brr[0]);
        syB = new JButton(brr[1]);
        syC = new JButton(brr[2]);
        syD = new JButton(brr[3]);
        syE = new JButton(brr[4]);

        synButton.add(syA);
        synButton.add(syB);
        synButton.add(syC);
        synButton.add(syD);
        synButton.add(syE);

        synButton.setBounds(100,150,600,100);

        translation = new JButton("Translate!");
        translation.setBounds(10,325,100,50);

        JPanel foreignTags = new JPanel(new GridLayout(1,5));
        foreignTags.setBounds(300,400,400,100);
        foreign = new JTextArea[5];

		foreign[0] = new JTextArea(5,1);
		foreign[0].setText("toothbrush");
		foreign[0].setFont(new Font("Arial", Font.BOLD, 16));
		foreign[0].setBackground(Color.orange);

		foreign[1] = new JTextArea(5,1);
		foreign[1].setText("brosse à dents");
		foreign[1].setFont(new Font("Arial", Font.BOLD, 16));
		foreign[1].setBackground(Color.orange);

		foreign[2] = new JTextArea(5,1);
		foreign[2].setText("зубная щетка");
		foreign[2].setFont(new Font("Arial", Font.BOLD, 16));
		foreign[2].setBackground(Color.orange);

		foreign[3] = new JTextArea(5,1);
		foreign[3].setText("cepillo de dientes");
		foreign[3].setFont(new Font("Arial", Font.BOLD, 16));
		foreign[3].setBackground(Color.orange);

		foreign[4] = new JTextArea(5,1);
		foreign[4].setText("فرشاة اسنان");
		foreign[4].setFont(new Font("Arial", Font.BOLD, 16));
		foreign[4].setBackground(Color.orange);

        for(int i=0; i<foreign.length; i++) {
        	if(i == 0) {
        		foreign[i].setBorder(BorderFactory.createMatteBorder(3, 3, 3, 0, Color.black));
        	}else if(i == foreign.length-1) {
        		foreign[i].setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.black));
        	}else {
        		foreign[i].setBorder(BorderFactory.createMatteBorder(3, 3, 3, 0, Color.black));
        	}
        	foreignTags.add(foreign[i]);
        }

        translation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//          	  String newStr = syA.getText();
            	foreign[0].setText(EnglishAPI.EnglishAPI(textArea.getText()));
            	foreign[1].setText(FrenchAPI.FrenchAPI(textArea.getText()));
            	foreign[2].setText(RussiaAPI.RussiaAPI(textArea.getText()));
            	foreign[3].setText(SpanishAPI.SpanishAPI(textArea.getText()));
            	foreign[4].setText(ArabicAPI.ArabicAPI(textArea.getText()));
            }
          });

        container.add(translation);
        container.add(foreignTags);

		JButton nextOne = new JButton("Confirm");
		nextOne.setBounds(10,500,100,50);
		container.add(nextOne);
		nextOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//          	  String newStr = syA.getText();
				// 如果 _save 中存在有效值，则插入数据库
				if (!_save[0].isEmpty()) {
					try {
						System.out.println("foreign:"+foreign[0].getText());
						ManagerInterface.IMMI(
								_save[0], _save[1], _save[2], _save[3],
								new String[]{
										foreign[0].getText(), foreign[1].getText(), foreign[2].getText(),
										foreign[3].getText(), foreign[4].getText()
								},
								new String[]{
										syA.getText(), syB.getText(), syC.getText(), syD.getText(), syE.getText()
								}
						);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

        container.add(synButton);

        // 设置JFrame可见
        frame.setVisible(true);

		syA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newStr = syA.getText();
				syA.setText(CallOneAPI.callOneAPI(newStr));
			}
		});

		syB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newStr = syB.getText();
				syB.setText(CallOneAPI.callOneAPI(newStr));
			}
		});

		syC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newStr = syC.getText();
				syC.setText(CallOneAPI.callOneAPI(newStr));
			}
		});

		syD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newStr = syD.getText();
				syD.setText(CallOneAPI.callOneAPI(newStr));
			}
		});

		syE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newStr = syE.getText();
				syE.setText(CallOneAPI.callOneAPI(newStr));
			}
		});

		insert.addActionListener(new ActionListener() { // 监听器
			@Override
			public void actionPerformed(ActionEvent e) {
				new MyDialog();
			}
		});

		jButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (synList.isEmpty()) {
					checkedAll = true;
					textArea.setText(null);
					alarm.setFont(new Font("Times New Roman", Font.BOLD, 18));
					_save[0] = "";
					_save[1] = "";
					_save[2] = "";
					_save[3] = "";
				} else {


					// 从 synList 提取数据并更新 _save 和 textArea
					checkedAll = false;
					alarm.setFont(new Font("Times New Roman", Font.BOLD, 0));
					String[] a = synList.remove(0);

					_save[0] = a[0];
					_save[1] = a[1];
					_save[2] = a[2];
					_save[3] = a[3];

					textArea.setText(a[3]);
					textArea.setFont(new Font("SimSun", Font.BOLD, 18));

					// 更新按钮文本
					String[] arr = CallFiveAPI.callFiveAPI(a[3]).split(",");
					try {
						syA.setText(arr[0]);
						syB.setText(arr[1]);
						syC.setText(arr[2]);
						syD.setText(arr[3]);
						syE.setText(arr[4]);
					} catch (Exception e1) {
						System.err.println("Error updating synonyms: " + e1.getMessage());
					}
				}
			}
		});

	}

	class MyDialog extends JDialog {
	    public MyDialog() {
	        setVisible(true);
	        setBounds(500, 100, 500, 150);
	        // 创建文本域用于输入
	        // 货架编号
	        JTextArea cabinetID = new JTextArea("Cabinet ID");
	        cabinetID.setBounds(10,20,90,20);
	        cabinetID.setEditable(false);
	        JTextField textField = new JTextField(20);
	        textField.setBounds(10,50,90,20);

	        // 货架层数
	        JTextArea layerID = new JTextArea("Layer ID");
	        layerID.setBounds(120,20,90,20);
	        layerID.setEditable(false);
	        JTextField textField2 = new JTextField(20);
	        textField2.setBounds(120,50,90,20);

	        // 单价
	        JTextArea commodityPrice = new JTextArea("Price");
	        commodityPrice.setBounds(230,20,90,20);
	        commodityPrice.setEditable(false);
	        JTextField textField3 = new JTextField(20);
	        textField3.setBounds(230,50,90,20);

	        // 中文名
	        JTextArea chineseName = new JTextArea("Name");
	        chineseName.setBounds(340,20,90,20);
	        chineseName.setEditable(false);
	        JTextField textField4 = new JTextField(20);
	        textField4.setBounds(340,50,90,20);

	        JButton confirm = new JButton("->");
	        confirm.setFont(new Font("Times New Roman", Font.BOLD, 6));
	        confirm.setBounds(440,20,40,50);

	        confirm.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	_cabinetID = textField.getText();
	            	_layerID = textField2.getText();
	            	_commodityPrice = textField3.getText();
	            	_chineseName = textField4.getText();
					_save[0] = _cabinetID;
					_save[1] = _layerID;
					_save[2] = _commodityPrice;
					_save[3] = _chineseName;
	            	synList.add(new String[] {_cabinetID, _layerID, _commodityPrice, _chineseName});
//	            	_newItem = true;
	            	dispose();
	            }
	        });




	        Container container = getContentPane();
	        container.setLayout(null);
	        container.add(cabinetID);
	        container.add(textField);
	        container.add(layerID);
	        container.add(textField2);
	        container.add(commodityPrice);
	        container.add(textField3);
	        container.add(chineseName);
	        container.add(textField4);
	        container.add(confirm);
	        container.setBackground(Color.lightGray);
	    }
	}


    public static void main(String[] args) {
		start = 0;
    	synList.add(new String[]{"B","3","7.5","牙刷"});
		_save[0] = "B";
		_save[1] = "3";
		_save[2] = "7.5";
		_save[3] = "牙刷";
        new Window(new String[] {"洁牙器","牙齿软刷","牙齿毛刷","刷牙用具","口腔清洁器"});
    }
}