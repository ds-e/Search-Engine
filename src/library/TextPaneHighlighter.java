package library;

import java.awt.Color;
import java.util.Random;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;



public class TextPaneHighlighter {
	// permet de surligner les mots recherches par l'utilisateur
	public void highlightall(JTextPane textComp, String[] search) {
	    removeHighlights(textComp);
	    
	 	for (String s:  search) {
			highlight(textComp,  s ,  generate_random_color());
		}
		
	}
	
	public Color generate_random_color() {
		Random random = new Random();
		final float hue = random.nextFloat();
		final float saturation = (random.nextInt(2000) + 1000) / 10000f;
		final float luminance = 0.9f;
		final Color color = Color.getHSBColor(hue, saturation, luminance);
		return color;
	}

	
	public void highlight(JTextPane textComp, String pattern, Color c) {
		Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(c);
	    try {
	        Highlighter hilite = textComp.getHighlighter();
	        Document doc = textComp.getDocument();
	        String text = doc.getText(0, doc.getLength());
	        int pos = 0;

	    
	        while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0)
	        {
	            hilite.addHighlight(pos, pos+pattern.length(), myHighlightPainter);
	            pos += pattern.length();
	        }
	    } catch (BadLocationException e) {
	    }
	}

	public void removeHighlights(JTextPane textComp){
	    Highlighter hilite = textComp.getHighlighter();
	    Highlighter.Highlight[] hilites = hilite.getHighlights();
	    for (int i=0; i<hilites.length; i++)
	    {
	        if (hilites[i].getPainter() instanceof MyHighlightPainter)
	        {
	            hilite.removeHighlight(hilites[i]);
	        }
	    }
	}

	
	class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
	    public MyHighlightPainter(Color color)
	    {
	        super(color);
	    }
	}
	
}