/*
 * Deling.java
 *
 * Created on 10 december 2005, 14:09
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package berekenen;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

/**
 *
 * @author Dries
 */
    
public class Deling extends Applet implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4598283251811595636L;
	Panel p1 = new Panel();
	Panel p2 = new Panel();
        ScrollPane p3 = new ScrollPane();
        
	Button knop = new Button("="); 
        Button knopl = new Button("leeg"); 
	TextField deeltal = new TextField(20);
	TextField deler = new TextField(20);
	TextField errmsg = new TextField(80);
	String dlt;
        String dlr;
        StringBuffer hdlt;
        StringBuffer hdlr;
        StringBuffer antwoord;
        StringBuffer product;
        int p;
        int getal;
        int antw;
        
        
        MyHok hok[][];
               
        int rij = 100;
	int kol = 100;
	public  void init () {
            GridBagLayout gridbag = new GridBagLayout();
            GridBagConstraints c = new GridBagConstraints();
            setLayout(gridbag);
		
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1.0;
            c.weighty = 1.0;
            c.gridwidth = GridBagConstraints.REMAINDER;
            gridbag.setConstraints(p1,c);
            p1.setLayout(new FlowLayout());	
            p1.setBackground(Color.red);
            p1.add(deeltal);
            p1.add(new Label(" gedeeld door "));
            p1.add(deler);
            p1.add(knop); 
            p1.add(knopl); 
            p1.add(errmsg);
            add(p1);
		
            c.weighty = 20.0;
            c.gridwidth = GridBagConstraints.REMAINDER;
            c.gridheight = GridBagConstraints.REMAINDER;
            gridbag.setConstraints(p3,c);
		
		
            p2.setLayout(new GridLayout(rij,kol));
            p2.setSize(rij * 20, kol * 20);
        //  p2.setBackground(Color.blue);
	             

            hok = new MyHok[rij][kol];
            for (int i=0;i<rij;i++) 
                for (int j=0;j<kol;j++) {
                    hok[i][j] = new MyHok(" ");
                    p2.add(hok[i][j]);
                    } 
            
            p3.add(p2);
            add(p3);
            
           
            setSize(800,600);
		
            knop.addActionListener(this);
            knopl.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == knop) {
                        verwerking();
			}
                if (e.getSource() == knopl) {
			nieuwveld();
			}
	}
        void nieuwveld() {
            for (int i=0;i<rij;i++) 
                    for (int j=0;j<kol;j++) {
                        hok[i][j].leeg();
                    }    
        }
        void verwerking() {
            nieuwveld();
            errmsg.setText("");
            dlt = deeltal.getText();
            dlr = deler.getText();
            
            try {
                for (int i=0;i<dlt.length();i++) {
                    Integer.parseInt(dlt.substring(i,i+1));
                }
                for (int i=0;i<dlr.length();i++) {
                    Integer.parseInt(dlr.substring(i,i+1));
                }
                
            } catch (NumberFormatException e) {
                errmsg.setText("Ongeldige invoer");
            } 
            
            
            // rv en kv geven de positie van hdlt op het scherm weer
            int rv = 1;
            int kv = dlr.length() + 2;
            // zet de deler op het scherm, zet er '/' achter,
            // zet er dan het deeltal achter, en dan '\'
            try {
            ZetOpScherm(dlr, 1, 1);
            ZetOpScherm("/", 1, dlr.length() + 1);
            ZetOpScherm(dlt, 1, dlr.length() + 2);
            ZetOpScherm("\\", 1, dlr.length() + 2 + dlt.length());
            antw = dlr.length() + 3 + dlt.length();        
            
            //zet deler onder het deeltal:
            p = 0;
            hdlt = new StringBuffer();
            hdlr = new StringBuffer(dlr);
            antwoord = new StringBuffer();
                        
            while (p<dlt.length() & kleiner(hdlt,hdlr)) {
                hdlt = hdlt.append(dlt.substring(p,p + 1));p++;
            }
            while (!kleiner(hdlt,hdlr)) {
                getal = 1; product = hdlr;
                while (!kleiner(hdlt,product) & getal < 10) 
                    product = keer(hdlr,++getal);
                product = keer(hdlr,--getal);
                antwoord = antwoord.append(String.valueOf(getal));
                int beginstreep = kv + p - 1 - hdlt.length();
                hdlt = verschil(hdlt,product);
                rv++;
                ZetOpSchermEnd(String.valueOf(product), rv, kv + p - 1);
            
                // zet er een streep onder
                rv++;
                for (int i = beginstreep; i < kv + p - 1;i++)
                    hok[rv - 1][i].streep();
                rv++;
                while (p<dlt.length() & kleiner(hdlt,hdlr)) {
                     hdlt = hdlt.append(dlt.substring(p,p + 1));p++;
                     if (kleiner(hdlt,hdlr))
                          antwoord = antwoord.append(String.valueOf(0));
                }
                ZetOpSchermEnd(String.valueOf(hdlt),rv,kv + p - 1);
            }//while
            if (antwoord.length() == 0) 
                antwoord = antwoord.append(String.valueOf(0));
            ZetOpScherm(String.valueOf(antwoord),1,antw);
            }  //try
            catch (OutOfScreenException e) {
               errmsg.setText(e.toString());
            }    
                       
        }
         void ZetOpScherm(String str,int rowpos,int kolpos) 
         throws OutOfScreenException {
             // zet string op scherm op bepaalde positie
             int rp = rowpos;
             int kp = kolpos;
             if (rp < 1) {
                 throw new OutOfScreenException("rij-waarde te klein");
             }
             else if (rp > rij) {
                 throw new OutOfScreenException("rij-waarde te groot");
             }
             else if (kp < 1) {
                 throw new OutOfScreenException("kol-waarde te klein");
             }
             else if (kp > kol) {
                 throw new OutOfScreenException("kol-waarde te groot");
             }
             else if (kp + str.length() - 1 > kol) {
                 throw new OutOfScreenException("past niet op scherm");
             }
             else {rp--;kp--;for (int i=0;i<str.length();i++) {
                hok[rp][kp++].upd(str.substring(i,i + 1));
            }};
        }
         void ZetOpSchermEnd(String str,int rowpos,int kolpos) 
         throws OutOfScreenException {
             // zet string op scherm op bepaalde eindpositie
             int rp = rowpos;
             int kp = kolpos - str.length() + 1;
             ZetOpScherm(str,rp, kp);
         }
        
        boolean kleiner(StringBuffer s1,StringBuffer s2) {
             StringBuffer sb1;
             StringBuffer sb2;
             sb1 = shift(s1);
             sb2 = shift(s2);
             
             if (sb1.length() < sb2.length()) return true;
             else if (sb1.length() > sb2.length()) return false;
             else if (new String(sb1).compareTo(new String(sb2)) < 0) 
                 return true;
             else return false;
         } 
          
         StringBuffer shift (StringBuffer s1) {
             // verwijderen voorloopnullen
             StringBuffer sb1 = s1;
             if (sb1.length() > 1) 
                while (sb1.length() > 1 & sb1.charAt(0) == '0')
                    sb1 = new StringBuffer(sb1.substring(1,sb1.length()));
             return sb1;
         }
         
         
         StringBuffer keer(StringBuffer sb,int gt) {
             byte b[] = new byte[sb.length()];
             byte c[] = new byte[b.length + 1];
             for (int i=0;i<b.length;i++) {
                 b[i] =  new Byte(sb.substring(i,i+1)).byteValue();
             }
             byte onthouden = 0;
             int res;
             int prod;
             for (int tel=b.length - 1;tel>=0;tel--)
             {prod = b[tel]*gt + onthouden; res = prod%10;
              c[tel+1] = (byte) res;
              onthouden = (byte) Math.floor(prod/10);
             }
             c[0] = onthouden;
             char ch[] = new char[c.length];
             for (int tel=0;tel<c.length;tel++)
             switch (c[tel]) {
                 case 0: ch[tel] = '0';
                 break;
                 case 1: ch[tel] = '1';
                 break;
                 case 2: ch[tel] = '2';
                 break;
                 case 3: ch[tel] = '3';
                 break;
                 case 4: ch[tel] = '4';
                 break;
                 case 5: ch[tel] = '5';
                 break;
                 case 6: ch[tel] = '6';
                 break;
                 case 7: ch[tel] = '7';
                 break;
                 case 8: ch[tel] = '8';
                 break;
                 case 9: ch[tel] = '9';
                 break;
                 default:;
             }
                 
              
             if (onthouden==0) 
                 return new StringBuffer(String.valueOf(ch,1,b.length));
             else 
                 return new StringBuffer(String.valueOf(ch)); 
         }    
         StringBuffer verschil(StringBuffer sb1,StringBuffer sb2) {
             StringBuffer s1;
             StringBuffer s2;
             s1 = shift(sb1);
             s2 = shift(sb2);
             byte b[];
             byte c[];
             if (kleiner(s2,s1)) {
                b = new byte[s1.length()];
                c = new byte[s1.length()];
                for (int i=0;i<b.length;i++) 
                    b[i] =  new Byte(s1.substring(i,i+1)).byteValue();
                int j = s2.length() - 1;
                for (int i=c.length - 1;i>=c.length - s2.length();i--) {
                    c[i] =  new Byte(s2.substring(j,j+1)).byteValue();
                    j--;
                }
             }
             else {
                b = new byte[s2.length()];
                c = new byte[s2.length()];
                for (int i=0;i<b.length;i++) 
                    b[i] =  new Byte(s2.substring(i,i+1)).byteValue();
                int j = s2.length() - 1;
                for (int i=c.length - 1;i>=c.length - s1.length();i--) {
                    c[i] =  new Byte(s1.substring(i,i+1)).byteValue();
                    j--;
                }
             }
             byte d[] = new byte[b.length];
             for (int i=b.length - 1;i>=0;i--)
                 if (b[i] < c[i]) {
                     d[i] = (byte) (10 + b[i] - c[i]);
                     int j;
                     for (j=i-1;b[j]==0;j--) b[j] = 9;
                     b[j]--;
                 }
                 else
                     d[i] = (byte) (b[i] - c[i]);
             char ch[] = new char[d.length];
             for (int tel=0;tel<d.length;tel++)
             switch (d[tel]) {
                 case 0: ch[tel] = '0';
                 break;
                 case 1: ch[tel] = '1';
                 break;
                 case 2: ch[tel] = '2';
                 break;
                 case 3: ch[tel] = '3';
                 break;
                 case 4: ch[tel] = '4';
                 break;
                 case 5: ch[tel] = '5';
                 break;
                 case 6: ch[tel] = '6';
                 break;
                 case 7: ch[tel] = '7';
                 break;
                 case 8: ch[tel] = '8';
                 break;
                 case 9: ch[tel] = '9';
                 break;
                 default:;
             }              
             return shift(new StringBuffer(String.valueOf(ch)));  
         }
        
}



	
