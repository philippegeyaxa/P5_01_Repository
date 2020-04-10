package projets.safetynet.endpoint;

import java.util.ArrayList;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import projets.safetynet.service.data.DataReadService;
import projets.safetynet.service.util.LogService;

@RestController
@RequestMapping("/phoneAlert")
public class PhoneAlertEndpoint {

	@Autowired 
	private DataReadService service;

	@GetMapping("")
	public ArrayList<String> getPhoneAlertResponse(@RequestParam(value = "firestation") long station) {
        LogService.logger.debug("getPhoneAlertResponse() " + station);
		ArrayList<String> response = service.getPhoneAlertResponse(station);
		return response;
	}

	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Wrong request !")
	@ExceptionHandler(ServletRequestBindingException.class)	 
	public void badRequest() {
		LogService.logger.error("badRequest() ServletRequestBindingException");
		return;
	}

	@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Wrong request !")
	@ExceptionHandler(TypeMismatchException.class)
	public void badParameterType() {
		LogService.logger.error("badParameterType() TypeMismatchException");
		return;
	}
	
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, 
			reason="Unknown error : revert to IT for investigation !")
	@ExceptionHandler(Exception.class)
	public void unknownError() {
		LogService.logger.error("unknownError() Exception");
		return;
	}
	
}