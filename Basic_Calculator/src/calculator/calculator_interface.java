package calculator;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

public class calculator_interface implements ActionListener{
	
	Button[] numbers = new Button[10]; 
	Button[] operations = new Button[4];
	Button[] others = new Button[3];
	Button decimal = new Button(".");
	
	Frame calcFrame = new Frame("CALCULATOR");
	
	Panel mainPanel = new Panel();
	Panel buttonPanel = new Panel();
	Panel textPanel = new Panel();
	Panel numberPanel = new Panel();
	Panel opPanel = new Panel();
	Panel sidePanel = new Panel();
	TextField display = new TextField();
	TextField answer = new TextField();
	Font font = new Font("Time", Font.BOLD, 12);
	Font font2 = new Font("Time", Font.BOLD, 18);
	ArrayList<String> expression = new ArrayList<>();


	public static void main(String[] args)
	{
		calculator_interface obj = new calculator_interface();

		obj.initGUI();
	}
	
	public void initGUI()
	{
				
		calcFrame.setSize(500,500);
		display.setFont(font);
		answer.setFont(font2);
		
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(textPanel, BorderLayout.NORTH);
	
		
		//two sub panels sizing
		textPanel.setPreferredSize(new Dimension(500,100)); 
		textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setPreferredSize(new Dimension(500,400));
		buttonPanel.setLayout(new BorderLayout());
		
		
		//adding textField to textPanel
		
		display.setEditable(false);
		display.setPreferredSize(new Dimension(480, 30));
		textPanel.add(display);
		
		answer.setEditable(false);
		answer.setPreferredSize(new Dimension(480, 50));
		textPanel.add(answer);
		
		//adding subPanels to buttonPanel
		buttonPanel.add(numberPanel, BorderLayout.WEST);
		buttonPanel.add(opPanel, BorderLayout.CENTER);
		buttonPanel.add(sidePanel, BorderLayout.EAST);
		
		numberPanel.setPreferredSize(new Dimension(300,400));
		sidePanel.setPreferredSize(new Dimension(100,400));
		
		//buttonPanel sub panel coloring (discerning)
		numberPanel.setBackground(Color.WHITE);
		opPanel.setBackground(Color.GRAY);
		sidePanel.setBackground(Color.GRAY);
		
		//set grid layout for all sub panels of buttonPanel
		numberPanel.setLayout(new GridBagLayout());
		opPanel.setLayout(new GridBagLayout());
		sidePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//add numbers to numberPanel
		for(int i = 0; i < 10; i++)
		{
			numbers[i] = new Button(String.valueOf(i)); //generate button 0-9
			numbers[i].addActionListener(this); //it will listen to user's action
					
		}
		
		c.insets = new Insets(10,10,10,10);
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		
		for(int i = 1; i <= 9; i++)
		{
			c.gridx = (i - 1) % 3;
			c.gridy = (i - 1) / 3;
			numberPanel.add(numbers[i], c);
		}
		
		c.gridx = 1;
		c.gridy = 3;
		numberPanel.add(numbers[0], c);
		
		
		c.gridx = 2;
		c.gridy = 3;
		numberPanel.add(decimal, c);
		
		//add operation buttons
		
		c.insets = new Insets(5,5,5,5);
		
		operations[0] = new Button("+");
		operations[1] = new Button("-");
		operations[2] = new Button("*");
		operations[3] = new Button("/");
		
		for(int i = 0 ; i < 4 ; i++)
		{
			operations[i].addActionListener(this);
		}
		
		decimal.addActionListener(this);
		
		for(int i = 0; i < 4; i++)
		{
			c.gridx = 0;
			c.gridy = i;
			opPanel.add(operations[i],c);
		}
		
		//other buttons
		others[0] =  new Button("AC");
		others[1] = new Button("<<");
		others[2] = new Button("=");
		c.gridy = 5;
		c.ipady = 100;
		opPanel.add(others[0], c);
		
		c.gridx = 0;
		c.gridy = 0;
		sidePanel.add(others[1], c);
		
		c.gridy = 1;
		c.ipady = 300;
		sidePanel.add(others[2], c);
		
		for(int i = 0; i < 3; i++)
		{
			others[i].addActionListener(this);
		}
		
		
		
		calcFrame.add(mainPanel);	
		calcFrame.setVisible(true);
		
		//allow program to automatically receive focus from keyboard
		calcFrame.setFocusable(true);
		calcFrame.requestFocus();
		textPanel.setFocusable(true);
		textPanel.requestFocus();
		display.setFocusable(true);
		display.requestFocusInWindow();
		
		
		calcFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
			
			
		}); 
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//Class to allow calculation
		calculation calc = new calculation();
		
			
		//KEY BINDING FOR NUMBERS		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(
				x -> { //use lambda because when addKeyEventDispatcher, x is KeyEvent
					if(x.getID() == KeyEvent.KEY_PRESSED)
					{
						if(x.getKeyCode() >= KeyEvent.VK_0 && x.getKeyCode() <= KeyEvent.VK_9)
						{
							String digit = String.valueOf(x.getKeyChar());

							int index = digit.charAt(0) - '0';

							ActionEvent dummyEvent = new ActionEvent(numbers[index], ActionEvent.ACTION_PERFORMED, digit);

							numberAction(dummyEvent, calc);
							return true;
						}

					}
					return false;

				});

		
		//Actions performed for all buttons
		numberAction(e, calc);
		operationAction(e, calc);
		decimalAction(e, calc);
		equalAction(e, calc);
		undoAction(e, calc);
		clearAction(e);
		
	}
	
	
	public void numberAction(ActionEvent e, calculation calc )
	{
		for(int i = 0 ; i < 10 ; i++)
		{
			if(e.getSource() == numbers[i])
			{
				String currentText = display.getText();
				System.out.println("PRINTING: " + currentText);
				String update = e.getActionCommand();
				System.out.println("NUMBER TYPED: " + update);
				
				String updated = calc.editDisplay(currentText, update);
				display.setText(updated);
				
			}
			
		}
	}
	
	public void undoAction(ActionEvent e, calculation calc)
	{
		if(e.getSource() == others[1])
		{
			if(!(display.getText().isEmpty()))
			{
				String currentText = display.getText();
				String update = calc.undoDisplay(currentText);
				display.setText(update);
				System.out.println("Undo result: " + update);
			}
			else
			{
				System.out.println("DISPLAY IS EMPTY!");
			}
			
			
		}
	}
	
	public void decimalAction(ActionEvent e, calculation calc) 
	{
		if(e.getSource() == decimal)
		{
			
			if(calc.lastCharCheck(display.getText()))
			{
				String currentText = display.getText();
				System.out.println("PRINTING: " + currentText);
				String update = e.getActionCommand();
				System.out.println("OPERATION TYPED: " + update);
				
				String updated = calc.editDisplay(currentText, update);
				display.setText(updated);
			}
			
		}
	}
	
	public void equalAction(ActionEvent e, calculation calc) 
	{
		if(e.getSource() == others[2])
		{
			if(calc.lastCharCheck(display.getText()))
			{
				String update;
				String currentText = display.getText();
				System.out.println("CURRENT EXPRESSION: " + currentText);
				expression = calc.splitter(currentText);
				System.out.println("CALCULATION IN PROCESS");
				System.out.println("THE ARRAYLIST: " + expression);
				
				double result = calc.PEMDAS(expression);				
				
				if(result == (int) result)
				{
					update = String.valueOf((int)result);
				}
				else
				{
					update = String.valueOf(result);
				}
				
				
				System.out.println("RESULT: " + result);
				answer.setText(update);
				
			}
		}
	}
	
	public void operationAction(ActionEvent e, calculation calc) 
	{
		for(int i = 0 ; i < 4; i++)
		{
			if(e.getSource() == operations[i])
			{
				if(calc.lastCharCheck(display.getText()))
				{
					String currentText = display.getText();
					System.out.println("PRINTING: " + currentText);
					String update = e.getActionCommand();
					System.out.println("OPERATION TYPED: " + update);
					
					String updated = calc.editDisplay(currentText, update);
					display.setText(updated);
				}
			}
		}
	}
	
	public void clearAction(ActionEvent e)
	{
		if(e.getSource() == others[0])
		{
			if(!(display.getText().isEmpty()) || !(answer.getText().isEmpty()))
			{
				display.setText(null);
				answer.setText(null);
				System.out.println("CLEARED!");
			}
			else
			{
				System.out.println("DISPLAY ALREADY EMPTY");
			}
		}
	}
	
}
