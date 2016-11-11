import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

public class LogConexion {

	public static void main(String[] args){
		Runtime r = Runtime.getRuntime();
		PrintWriter file=null;
		try {
			file = new PrintWriter("./Conexion.log");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String comando = "cmd /c ping -t 8.8.8.8";
		Process p=null;
		try {
			p = r.exec( comando );
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(
									new InputStreamReader (is));
			String linea;
			linea = br.readLine();
			while ( ( linea = br.readLine() )!=null ){
				if (linea.substring(0, 5).equals("Tiempo")){
					Date fecha = new Date();
					file.println(fecha.toString()+"Error en la transmisión, conexión caída.");
					System.out.println(fecha.toString()+"Error en la transmisión, conexión caída.");
				}
				System.out.println(linea);
			}
			br.close();
		}catch (Exception e){
			System.out.println("Error en : "+comando);
			e.printStackTrace();
		}
		int salida;
		try{
			salida = p.waitFor();
			System.out.println("Valor devuelto: "+salida);
		}catch(InterruptedException e){
			System.out.println("El comando terminó bruscamente:");
			e.printStackTrace();
		}
		try{
			InputStream errorStream = p.getErrorStream();
			BufferedReader errorBufferR =
					new BufferedReader(new InputStreamReader(errorStream));
			String lineaError;
			while ( ( lineaError = errorBufferR.readLine() )!=null ){
				System.out.println("Error: "+lineaError);
			}
		}catch (IOException ioe){
			ioe.printStackTrace();
		}
		
	}

}
