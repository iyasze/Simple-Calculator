package calculator;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

/*THINGS LEFT TO DO
 * add function to all buttons
 * calculating system
 * 		when equal button is pressed, text from display is taken
 * 		gather into an arrayList
 * 		do the calculation (how? to be decided) but it follows PEMDAS
 * 
 * check system
 * 		when any of the button is pressed, run a special method to update: display area AND arrayList
 * 		when operation button is pressed run a checking: if last element on array list is !isDigit don't update anything
 * 		display an error message AND do not start calculation
 * 
 * undo button
 * 		check display, take the full text, remove last character, update the display, update the arrayList
 * 
 * 
 * EASIEST WAY TO MANAGE ARRAYLIST: EVERY UPDATE, EMPTY THE ARRAYLIST, UPDATE A NEW ARRAYLIST
 * */


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
	
	
	public static void main(String[] args)
	{
		calculator_interface obj = new calculator_interface();
		
		obj.mainFrame();
	}
	
	public void mainFrame()
	{
				
		calcFrame.setSize(500,500);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(textPanel, BorderLayout.NORTH);
		
		//sub panel coloring (for discerning)
		buttonPanel.setBackground(Color.RED);
		textPanel.setBackground(Color.BLUE);
		
		//two sub panels sizing
		textPanel.setPreferredSize(new Dimension(500,100)); //setPreferredSize for sizing
		textPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setPreferredSize(new Dimension(500,400));
		buttonPanel.setLayout(new BorderLayout());
		
		
		//adding textField to textPanel
		
		display.setEditable(false);
		display.setPreferredSize(new Dimension(480, 90));
		textPanel.add(display);
		
		//adding subPanels to buttonPanel
		buttonPanel.add(numberPanel, BorderLayout.WEST);
		buttonPanel.add(opPanel, BorderLayout.CENTER);
		buttonPanel.add(sidePanel, BorderLayout.EAST);
		
		numberPanel.setPreferredSize(new Dimension(300,400));
		sidePanel.setPreferredSize(new Dimension(100,400));
		
		//buttonPanel sub panel coloring (discerning)
		numberPanel.setBackground(Color.CYAN);
		opPanel.setBackground(Color.CYAN);
		sidePanel.setBackground(Color.CYAN);
		
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
		//add numbers to the panel
		
		//add operation buttons
		
		c.insets = new Insets(5,5,5,5);
		
		operations[0] = new Button("+");
		operations[1] = new Button("-");
		operations[2] = new Button("x");
		operations[3] = new Button("/");
		
		for(int i = 0 ; i < 4 ; i++)
		{
			operations[i].addActionListener(this);
		}
		
		
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
		
		
		
		
		
		
		
		calcFrame.add(mainPanel);
		
		calcFrame.setVisible(true);
		
		
		calcFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
			
			
		}); //important as it can terminate the whole program when terminating the window
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		calculation calc = new calculation();
		
		//number buttons
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
	
}
