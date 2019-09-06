package kartify.ezetap.printer;

import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShellExecuter {

	public ShellExecuter() {

	} 

	public String Executer(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";			
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		String response = output.toString();
		return response;

	}

	public void hideBar()
	{

		Process proc = null;

		String ProcID = "79"; //HONEYCOMB AND OLDER

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
			ProcID = "42"; //ICS AND NEWER
		}

		try {
			proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "service call activity "+ProcID+" s16 com.android.systemui" });
		} catch (Exception e) {
			Log.w("Main","Failed to kill task bar (1).");
			e.printStackTrace();
		}
		try {
			proc.waitFor();
		} catch (Exception e) {
			Log.w("Main","Failed to kill task bar (2).");
			e.printStackTrace();
		}
	}

	public void showBar()
	{
		Process proc = null;
		try {
			proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "am startservice -n com.android.systemui/.SystemUIService" });
		} catch (Exception e) {
			Log.w("Main","Failed to kill task bar (1).");
			e.printStackTrace();
		}
		try {
			proc.waitFor();
		} catch (Exception e) {
			Log.w("Main","Failed to kill task bar (2).");
			e.printStackTrace();
		}
	}
}
