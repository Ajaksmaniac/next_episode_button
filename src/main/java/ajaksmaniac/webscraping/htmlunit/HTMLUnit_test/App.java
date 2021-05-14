package ajaksmaniac.webscraping.htmlunit.HTMLUnit_test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.gargoylesoftware.css.parser.CSSErrorHandler;
import com.gargoylesoftware.css.parser.CSSException;
import com.gargoylesoftware.css.parser.CSSParseException;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCssErrorHandler;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;

import com.gargoylesoftware.htmlunit.html.HtmlPage;


/**
 * Hello world!
 *
 */
public class App  
{
	 static List<Episode> allEpisodes;
    public static void main( String[] args )
    {
    	JFrame f = new JFrame("Next episode player");
    	JButton b = new JButton("Next episode");
    	  b.setBounds(50,50,150,30);  
    	 f.add(b);  
    	    f.setSize(250,150);  
    	    f.setLayout(null);  
    	    f.setVisible(true);   
    	    allEpisodes = getAllEpisodes();
    	    b.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Episode next= getNextEpisode();
						Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome "+next.getUrl()});
				    	System.out.println(next);
				    	//markEpisodeAsLastWatched(next, allEpisode);
					
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
    	
//    	downloadEpisodes();
    	
    	
    	//allEpisode.forEach(e -> System.out.println(e));
    	//TO DO add getNextEpisode;
    	
    	
    	
    	       
    }    
    
    public static Episode getNextEpisode() {
    	
    	
    	
    	for (Episode episode : allEpisodes) {
			if(episode.lastWatched) {
				episode.setLastWatched(false);
				int id = episode.getId();
				Episode next = allEpisodes.get(id+1);
				allEpisodes.get(id+1).setLastWatched(true);
				markEpisodeAsLastWatched(next);
				return next;
			}
		}
		return null;
    }
    
    public static void markEpisodeAsLastWatched(Episode lastWatched) {
    	try(BufferedWriter bw = new BufferedWriter( new FileWriter(new File(System.getProperty("user.dir")+"/src/main/resources/bbtf.txt")))) {
			for (Episode episode : allEpisodes) {
				if(episode.equals(lastWatched)) {
					bw.write(episode.getId() + " " + episode.getUrl()+ " w"+"\n");
					episode.setLastWatched(true);
				}else {
					bw.write(episode.getId() + " " + episode.getUrl()+"\n");
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    static String getNumber(int number) {
    	
    		return String.format("%02d", number);
    	
    }
    static public List<Episode> getAllEpisodes(){
    	try (BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.dir")+"/src/main/resources/bbtf.txt")))) {
    		List<Episode> episodes = new ArrayList<>();
    		String line = br.readLine();
    		while(line != null) {
    			String[] arr = line.split("\\s+");
    			//System.out.println(line);
    			boolean lastWatched = false;;;
    			if(arr.length > 2) {
    				 lastWatched = (arr[2].equals("w"))? true:false;
    			}
    			line = br.readLine();
    			Episode e = new Episode(Integer.parseInt(arr[0]), arr[1], lastWatched);
    			System.out.println(e);
    			episodes.add(e);
    			
    		}
    		return episodes;
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
		
    }
    
    public static void downloadEpisodes() {
    	 try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {

    	     
				webClient.getOptions().setJavaScriptEnabled(false);
				webClient.getOptions().setCssEnabled(false);;
			  
		
				int season = 5;
				int episode = 1;
				int id = 0;
			  HtmlPage page = null;
			  
				  
			 
			
			 	outer:while(true) {
			 		if(season > 12 ) {
			 			 break outer;
			 		 }
			 		 //Thread.sleep(1000);
			 		String url = "https://www.filmotip.com/search.php?keywords=Big+Bang+Theory+s"+getNumber(season)+"e"+getNumber(episode)+"&btn=Search";
			 		//System.out.println(season);
			 		//System.out.println(url);
			 		page = webClient.getPage(url);
			 		 List<DomElement>contents =   page.getElementsByTagName("a");
			 		//System.out.println(contents.get(contents.size()-1));
			 		 fir:for (DomElement domElement : contents) {
						if(domElement.getAttribute("href").contains("https://www.filmotip.com/sa-prevodom/the-big-bang-theory")) {
							HtmlPage page2 =domElement.click();
							 System.out.println(id + " " +page2.getBaseURI());
							 id++;
							 episode++;
							 
							 continue outer;
						}
						
					}
			 		 
			 		 season++;
			 		 episode = 1;
 	        		
			 	}
	    	       
	    	       
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // Get the form that we are dealing with and within that form, 
	        // find the submit button and the field that we want to change.
    }
    
}
