package logs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

	private BufferedWriter bufWriter;
	private String path;
	private String name;
	
	public Log(String path, String name){
		super();
		this.path = path;
		this.name = name;
		this.addLine("\n\n----------------------------------\n");
	}
	
	private void open(boolean append) throws IOException {
		this.bufWriter = new BufferedWriter(new FileWriter(this.path, append));
	}
	
	private void close() throws IOException {
		this.bufWriter.close();
	}
	
	public void addLine(String line){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String formatoFecha = sdf.format(new Date());
		try {
			this.open(true);
			this.bufWriter.write("\n[" + formatoFecha + "] " + line);
			this.close();
		} catch (IOException e) {
			Logger.getLogger(this.name).log(Level.SEVERE, null, e);
		}
	}
	
	public void resetLog(){
		try {
			this.open(false);
			this.close();
		} catch (IOException e) {
			Logger.getLogger(this.name).log(Level.SEVERE, null, e);
		}
	}

}
