package cybersoft.javabackend.java18.gamedoanso;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class GameTest {
	private Game game;
	
	@BeforeAll
	public void setupTest () {
		game = new Game();
	}
	
	@Test
	public void shouldStartedSuccessfully () {
		assertDoesNotThrow(() -> game.start());
	}
}
