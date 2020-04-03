package projets.safetynet.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import projets.safetynet.model.core.FireStation;
import projets.safetynet.model.core.Person;
import projets.safetynet.model.url.FireStationResponse;
import projets.safetynet.service.DataCreateService;
import projets.safetynet.service.DataReadService;

@SpringBootTest
public class FireStationEndpointTest {

    @Autowired
    private FireStationEndpoint endpoint;

    @MockBean
    private DataReadService readService;

    @MockBean
    private DataCreateService createService;

    @Test
    public void givenResponse_whenGetFireStationResponse_thenReturnsExpectedResponse()
    {
    	// GIVEN
    	FireStationResponse expected = new FireStationResponse();
    	when(readService.getFireStationResponse(12345)).thenReturn(expected);
    	// WHEN
    	FireStationResponse response = endpoint.getFireStationResponse(12345);
    	// THEN
    	assertEquals(expected, response);
    }

    @Test
    public void givenNewFireStation_whenPostFireStationRequest_thenFireStationIsCreated()
    {
    	// GIVEN
		FireStation sNew = new FireStation();
    	when(createService.postFireStationRequest(sNew)).thenReturn(sNew);
    	// WHEN
    	ResponseEntity<FireStation> response = endpoint.postFireStationRequest(sNew);
    	// THEN
    	assertEquals(sNew, response.getBody());
    }

}
