package dbee_pj;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Canvas;
import java.awt.Panel;

public class inventory {

	private JFrame frame;
	private JTable table;
	private JComboBox txtproduct;
	private JTextField txtcode;
	private JTextField txtqty;
	private JTextField txtmin;
	private JTextField txtorder;
	private JTextField txtnew;
	private JTextField txtprice;
	private JTextField txtcost;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					inventory window = new inventory();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public inventory() {
		initialize();
		Connect();
	    table_load();
	    LoadCode();	
	}
	
	
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	

	
	public void Connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/beetoon", "root","1234");  // gui이름인 mysql과 연결
        }
        catch (ClassNotFoundException ex)  
        {
          ex.printStackTrace();
        }
        catch (SQLException ex)   
        {
            ex.printStackTrace();
        }
 
    }
	
	public void table_load() //조인 테이블
    {
     try
     {  
    pst = con.prepareStatement("select * from inventory;");
    rs = pst.executeQuery();
    table.setModel(DbUtils.resultSetToTableModel(rs)); 
    
     }
     catch (SQLException e)
     {
     e.printStackTrace();
     

     }
    }
	
	public void LoadCode()    
    { 
        try
        {
        	
        pst = con.prepareStatement("SELECT prod_name FROM inventory;");
        rs = pst.executeQuery();
         
        txtproduct.removeAllItems();

        while (rs.next())  
          {
        txtproduct.addItem(rs.getString("prod_name"));
          }
        }
        catch ( Exception e)
        {
           e.printStackTrace();
        }
    }
	
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 824, 617);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("재고");
		lblNewLabel.setBounds(39, 23, 103, 35);
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 30));
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(29, 55, 756, 497);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("상품:");
		lblNewLabel_1.setBounds(12, 43, 52, 15);
		panel.add(lblNewLabel_1);
		
		txtproduct = new JComboBox();
		txtproduct.setBounds(76, 39, 96, 23);
		panel.add(txtproduct);
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setBounds(193, 39, 95, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String product = txtproduct.getSelectedItem().toString();
	            
	             try
	                {
	                pst = con.prepareStatement( "SELECT * FROM inventory where prod_name = ?" ); 

	                pst.setString(1, product);  
	                rs = pst.executeQuery();
	                
	                
	                if ( rs.next() == true )
	                {
	             txtcode.setText(rs.getString(1));
	             txtqty.setText(rs.getString(2));  
	             txtmin.setText(rs.getString(3));
	             txtorder.setText(rs.getString(4));
	             
	             pst = con.prepareStatement( "SELECT * FROM inventory where prod_name = ?" ); 
	             pst.setString(1, product);  
	             rs = pst.executeQuery();
	             table.setModel(DbUtils.resultSetToTableModel(rs));

	                }
	             else
	             {
	                 JOptionPane.showMessageDialog(null, "Record Not Found");
	             }
	                
	                
	                }
	                catch ( Exception e1 )
	                {
	                e1.printStackTrace();
	                }
				
				
				
			}
		});
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("발주 신청");
		btnNewButton_1.setBounds(193, 93, 95, 51);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Refresh");
		btnNewButton_2.setBounds(627, 121, 95, 23);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtcode.setText("");
                txtqty.setText("");
                txtmin.setText("");
                txtorder.setText("");
                txtnew.setText("");
                txtprice.setText("");
                txtcost.setText("");
                txtcode.requestFocus();
                
                table_load();
			
			}
		});
		panel.add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 274, 697, 213);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		
		
		JLabel lblNewLabel_2 = new JLabel("상품코드:");
		lblNewLabel_2.setBounds(402, 23, 96, 15);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("현 재고량:");
		lblNewLabel_3.setBounds(402, 59, 83, 15);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("최소보유량:");
		lblNewLabel_4.setBounds(402, 90, 83, 15);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("발주개수:");
		lblNewLabel_5.setBounds(402, 125, 96, 15);
		panel.add(lblNewLabel_5);
		
		txtcode = new JTextField();
		txtcode.setBounds(487, 20, 106, 21);
		panel.add(txtcode);
		txtcode.setColumns(10);
		
		txtqty = new JTextField();
		txtqty.setBounds(487, 56, 106, 21);
		panel.add(txtqty);
		txtqty.setColumns(10);
		
		txtmin = new JTextField();
		txtmin.setBounds(487, 87, 106, 21);
		panel.add(txtmin);
		txtmin.setColumns(10);
		
		txtorder = new JTextField();
		txtorder.setBounds(487, 122, 106, 21);
		panel.add(txtorder);
		txtorder.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("Update");
		btnNewButton_3.setBounds(627, 19, 95, 23);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code,qty,min,order,product;
				code = txtcode.getText();
	            qty = txtqty.getText();
	            min = txtmin.getText();
	            order = txtorder.getText();
	            product=txtproduct.getSelectedItem().toString();
				
	            try {
	                pst = con.prepareStatement("update inventory set inv_num= ?,inv_qty=?,inv_min=?,inv_order=? where prod_name =?");
	                pst.setString(1, code);
	                pst.setString(2, qty);
	                pst.setString(3, min);
	                pst.setString(4,order);
	                pst.setString(5,product);
	                pst.executeUpdate();
	                            JOptionPane.showMessageDialog(null, "Update");
	                            table_load();
	                            LoadCode();
	                          
	                            txtcode.setText("");
	                            txtqty.setText("");
	                            txtmin.setText("");
	                            txtorder.setText("");
	                            txtcode.requestFocus();
	                }
	                 
	                            catch (SQLException e1) {
	                e1.printStackTrace();
	                }
	                
				
				
				
			}
		});
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_5 = new JButton("Delete");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String product;
				product=txtproduct.getSelectedItem().toString();
	   
	            try {
	            	pst = con.prepareStatement("delete from inventory where prod_name =?");
	                pst.setString(1, product);
	                pst.executeUpdate();
	                JOptionPane.showMessageDialog(null, "Delete");
	                table_load();
	                LoadCode();
	              
	                txtcode.setText("");
                    txtqty.setText("");
                    txtmin.setText("");
                    txtorder.setText("");
                    txtcode.requestFocus();
                    
               
	            }

	                catch (SQLException e1) {
	            e1.printStackTrace();
	            }
				
				
			}
		});
		btnNewButton_5.setBounds(627, 68, 95, 23);
		panel.add(btnNewButton_5);
		
		JLabel lblNewLabel_6 = new JLabel("New 상품:");
		lblNewLabel_6.setBounds(12, 181, 106, 15);
		panel.add(lblNewLabel_6);
		
		txtnew = new JTextField();
		txtnew.setBounds(76, 178, 96, 21);
		panel.add(txtnew);
		txtnew.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("Add");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code,qty,min,order,newproduct,price,cost;
				code = txtcode.getText();
	            qty = txtqty.getText();
	            min = txtmin.getText();
	            order = txtorder.getText();
	            newproduct=txtnew.getText();
	            price = txtprice.getText();
	            cost=txtcost.getText();
				
				
	            try {
	            	pst = con.prepareStatement("insert into product values(?,?,?);");
	            	pst.setString(1, newproduct);
	                pst.setString(2, price);
	                pst.setString(3, cost);
	                pst.executeUpdate();
	                
	            	pst = con.prepareStatement("insert into inventory values(?,?,?,?,?);");
	                pst.setString(1, code);
	                pst.setString(2, qty);
	                pst.setString(3, min);
	                pst.setString(4,order);
	                pst.setString(5,newproduct);
	                pst.executeUpdate();
	                JOptionPane.showMessageDialog(null, "ADD");
	                table_load();
	                LoadCode();
	                          
	                txtcode.setText("");
	                txtqty.setText("");
	                txtmin.setText("");
	                txtorder.setText("");
	                txtnew.setText("");
	                txtcode.requestFocus();
	                }
	                 
	                catch (SQLException e1) {
	                e1.printStackTrace();
	                JOptionPane.showMessageDialog(null, "Record Failed");
	                }
	         
			}
		});
		btnNewButton_4.setBounds(193, 177, 95, 23);
		panel.add(btnNewButton_4);
		
		JLabel lblNewLabel_7 = new JLabel("상품 가격:");
		lblNewLabel_7.setBounds(12, 208, 83, 15);
		panel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("재료원가:");
		lblNewLabel_8.setBounds(12, 235, 83, 15);
		panel.add(lblNewLabel_8);
		
		txtprice = new JTextField();
		txtprice.setBounds(76, 206, 96, 21);
		panel.add(txtprice);
		txtprice.setColumns(10);
		
		txtcost = new JTextField();
		txtcost.setBounds(76, 232, 96, 21);
		panel.add(txtcost);
		txtcost.setColumns(10);
		
		
	}
}
