package pd.fiit.subtitlenet;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import pd.fiit.reusable.HttpConn;

public final class LogInHandler implements Callable<String> {
	private static final Logger logger = Logger.getLogger(LogInHandler.class.getName());
	
	@Override
	public String call() throws Exception {
		String response = sendLogInRequest();
		boolean message = false;
		
		while (response == null || response.indexOf("Error") != -1) { // error?
			JOptionPane.showMessageDialog(null, "Can not connect to subtitle server, please check your connection.\nTrying to reconnect, please wait for message about successful login.", "Error", JOptionPane.ERROR_MESSAGE);
			Thread.sleep(3000);
			response = sendLogInRequest();
			message = true;
		}
		
		ResponseHandler handler = new ResponseHandler(response);
		String token = handler.getVariableValue("token");

		logger.log(Level.INFO, "log in successful.");
		if (message)
			JOptionPane.showMessageDialog(null, "LogIn to API server successful.", "Info", JOptionPane.INFORMATION_MESSAGE);
		
		return token;
	}

	/** tries to login to opensubtitles and gets token to proceed other requests */
	private String sendLogInRequest() {
		MethodWrapper xmlRequest = new MethodWrapper();
		String response = null;
		
		try { // try to login
			response = HttpConn.HttpPost("api.opensubtitles.org", "xml-rpc", xmlRequest.logIn());
		} catch (Exception e1) {
			logger.warning("can not obtain token from api server.");
			return null;
		}
		
		response = response.substring(response.indexOf("<?xml"));
		return response;
	}
}
