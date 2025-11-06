package es.upm.grise.profunduzacion.cruiseController;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.upm.grise.profundizacion.cruiseControl.CruiseControl;
import es.upm.grise.profundizacion.exceptions.IncorrectSpeedSetException;
import es.upm.grise.profundizacion.exceptions.SpeedSetAboveSpeedLimitException;

class CruiseControlTest {
	CruiseControl cruiseControl;

	@BeforeEach
	void setup() {
		cruiseControl = new CruiseControl(null); // No se va a usar Speedometer para estas pruebas, no hace falta mock
		// No se puede usar spy para probar solo el método, porque usa el atributo directamente y no los getter y setter.
	}

	@Test
	void validSpeedSetWithoutSpeedLimitTest() {
		int speed = 10;
		assertDoesNotThrow(() -> cruiseControl.setSpeedSet(speed));
		assertEquals(speed, cruiseControl.getSpeedSet());
	}

	@Test
	void validSpeedSetWithSpeedLimitTest() {
		int speed = 10;
		int limit = 20;
		cruiseControl.setSpeedLimit(limit);
		assertDoesNotThrow(() -> cruiseControl.setSpeedSet(speed));
		assertEquals(speed, cruiseControl.getSpeedSet());
	}

	@Test
	void zeroSpeedSetTest() {
		int speed = 0;
		assertThrows(IncorrectSpeedSetException.class,() -> cruiseControl.setSpeedSet(speed));
	}

	@Test
	void negativeSpeedSetTest() {
		int speed = -10;
		assertThrows(IncorrectSpeedSetException.class,() -> cruiseControl.setSpeedSet(speed));
	}


	@Test
	void aboveLimitSpeedSetTest() {
		int speed = 20;
		int limit = 10;
		cruiseControl.setSpeedLimit(limit);
		assertThrows(SpeedSetAboveSpeedLimitException.class,() -> cruiseControl.setSpeedSet(speed));
	}
}
